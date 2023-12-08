package com.jcdev.userapi.domain.service.implementation;

import com.jcdev.userapi.domain.entity.AuthenticationToken;
import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.domain.service.AuthenticationTokenService;
import com.jcdev.userapi.repository.AuthenticationTokenRepository;
import com.jcdev.userapi.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("authenticationTokenService")
public class AuthenticationTokenImpl implements AuthenticationTokenService {
    @Autowired
    @Qualifier("authenticationTokenRepository")
    private AuthenticationTokenRepository authenticationTokenRepository;


    @Override
    public AuthenticationToken create(User user) {
        String token = generateToken(user.getEmail());
        AuthenticationToken authToken = new AuthenticationToken(user, token);
        return authenticationTokenRepository.save(authToken);
    }

    public String generateToken(String email) {
        return JwtUtil.generateToken(email);
    }
}
