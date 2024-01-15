package com.vmg.ibo.core.service.security;

import com.vmg.ibo.core.utils.RequestUtils;
import com.vmg.ibo.core.model.entity.User;
import com.vmg.ibo.core.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class UserDetailService implements IUserDetailService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        } else {
            user.get().setLastIp(RequestUtils.getClientIP(request));
            user.get().setLastLogin(new Date());
            return new CustomUserDetail(user.get());
        }
    }
}
