package com.jcdev.userapi.domain.service;

import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.domain.model.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse create(User user);
    User findByEmail(String email);

    User update(User user);

    User setInactive(User user);


}
