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

@Service
public class UserDetailService implements IUserDetailService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private HttpServletRequest request;

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user = userRepository.findByEmail(email).get();
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
        }
        //todo late
        user.setLastIp(RequestUtils.getClientIP(request));
        user.setLastLogin(new Date());
        return new CustomUserDetail(user);
    }
}
