package com.jcdev.userapi.domain.service;

import com.jcdev.userapi.domain.entity.AuthenticationToken;
import com.jcdev.userapi.domain.entity.User;

public interface AuthenticationTokenService {
    AuthenticationToken create(User user);
    String generateToken(String email);
}
