package com.jcdev.userapi.domain.service;

import com.jcdev.userapi.domain.dto.UserDto;
import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.domain.dto.UserResponse;
import com.jcdev.userapi.domain.dto.UserToken;

public interface UserService {
    UserResponse create(UserDto userDto);
    User findByEmail(String email);

    User update(User user);

    User setInactive(User user);

    UserToken signIn(UserDto userDto);


}
