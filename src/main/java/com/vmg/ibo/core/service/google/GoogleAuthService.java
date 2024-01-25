package com.vmg.ibo.core.service.google;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmg.ibo.core.model.dto.JwtDTO;
import com.vmg.ibo.core.model.dto.UserInfoGoogleDto;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.repository.IUserRepository;
import com.vmg.ibo.core.service.security.JwtService;
import com.vmg.ibo.core.service.user.IUserService;
import com.vmg.ibo.customer.model.customer.RegisterModel;
import com.vmg.ibo.form.service.email.EmailService;

@Service
public class GoogleAuthService implements IGoogleAuthService {

    private static final Logger logger = LoggerFactory.getLogger(GoogleAuthService.class);

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Value("${google.token.url}")
    private String tokenUrl;

    @Value("${google.token.endpoint}")
    private String tokenEndpoint;

    @Value("${google.user.info.endpoint}")
    private String userInfoEndpoint;

    @Autowired
    private IUserRepository userRepository;


    @Autowired
    private IUserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private EmailService emailService;

    @Override
    public UserInfoGoogleDto getAccessToken(String authorizationCode, String redirectUri) {
        UserInfoGoogleDto userInfoGoogle = null;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Build the request parameters
        MultiValueMap<String, String> body =
                UriComponentsBuilder.newInstance()
                        .queryParam("code", authorizationCode)
                        .queryParam("client_id", clientId)
                        .queryParam("client_secret", clientSecret)
                        .queryParam(
                                "redirect_uri",
                                Objects.isNull(redirectUri) ? this.redirectUri : redirectUri)
                        .queryParam("grant_type", "authorization_code")
                        .build()
                        .getQueryParams();

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Create a RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        // Make a POST request to the token endpoint
        String response = restTemplate.postForObject(tokenEndpoint, requestEntity, String.class);

        // Parse the response JSON to extract the access token
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode;
        try {
            jsonNode = objectMapper.readTree(response);
            String accessToken = jsonNode.get("access_token").asText();

            headers.setBearerAuth(accessToken);
            headers.setContentType(MediaType.APPLICATION_JSON);

            RequestEntity<?> requestEntityUserInfo =
                    new RequestEntity<>(headers, HttpMethod.GET, URI.create(userInfoEndpoint));

            String userInfoGoogleJson =
                    restTemplate.exchange(requestEntityUserInfo, String.class).getBody();

            // Convert JSON to UserInfo object
            userInfoGoogle = objectMapper.readValue(userInfoGoogleJson, UserInfoGoogleDto.class);

            // Now userInfo contains the user information in JSON format
            logger.info(userInfoGoogle.getEmail());

        } catch (Exception e) {
            logger.info("Exception while fetching user information: " + e.getMessage());
        }

        return userInfoGoogle;
    }

    @Override
    @Transactional
    public JwtDTO authenByGoogle(String code, String redirectUri) {
        ObjectMapper objectMapper = new ObjectMapper();

        logger.info("authenticate google");
        UserInfoGoogleDto userInfoGoogle = this.getAccessToken(code, redirectUri);
        
        try {
            logger.info(objectMapper.writeValueAsString(userInfoGoogle));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
        Optional<User> userOp =
                userRepository
                        .findByEmail(userInfoGoogle.getEmail());
        User currentUser = userOp.orElse(null);
        
        if (!Objects.isNull(currentUser)) {
            if (!currentUser.isActive()) {
                throw new RuntimeException("User chưa được active");
            }
        } else {
            RegisterModel registerModel = new RegisterModel();
            registerModel.setEmail(userInfoGoogle.getEmail());
            registerModel.setPassword("12345678@Abc");
            registerModel.setConfirmPassword("12345678@Abc");
            currentUser = userService.registerUserByGoogle(registerModel);
            
        }

        List<String> permissions = new ArrayList<>();
        currentUser.getRoles()
            .forEach(
                    role -> {
                        role.getPermissions()
                                .forEach(
                                        permission -> {
                                            permissions.add(permission.getName());
                                        });
                    });
        Collection<SimpleGrantedAuthority> listPermissions = new ArrayList<SimpleGrantedAuthority>();
        permissions.stream().map(SimpleGrantedAuthority::new).forEach(listPermissions::add);
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser.getEmail(), currentUser.getPassword(), listPermissions);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String jwt = jwtService.generateJwtToken(currentUser.getEmail());
        

        if (!userOp.isPresent()) {
            emailService.sendEmail(currentUser.getEmail(), "[IB Online thông báo]", "[IB Online thông báo] Tài khoản của bạn đã được kích hoạt thành công. Cảm ơn quý khách đã tin tưởng sử dụng dịch vụ IB Online của HMG", Collections.emptyList());
        }
        return new JwtDTO(
                jwt,
                currentUser.getId(),
                currentUser.getEmail(),
                null,
                    currentUser.getChannelId());
        
    }
    
}
