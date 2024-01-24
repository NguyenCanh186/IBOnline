package com.vmg.ibo.core.service.google;

import com.vmg.ibo.core.model.dto.JwtDTO;
import com.vmg.ibo.core.model.dto.UserInfoGoogleDto;

public interface IGoogleAuthService {
    
    UserInfoGoogleDto getAccessToken(String authorizationCode, String redirectUri);

    JwtDTO authenByGoogle(String code, String redirectUri);
}
