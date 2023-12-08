package com.jcdev.userapi.domain.service;

import com.jcdev.userapi.domain.entity.User;

public interface UserService {
    User create(User user);
    User findByEmail(String email);

    User update(User user);

    User setInactive(User user);
}
