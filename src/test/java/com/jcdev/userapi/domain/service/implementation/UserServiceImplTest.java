package com.jcdev.userapi.domain.service.implementation;

import com.jcdev.userapi.domain.dto.PhoneDto;
import com.jcdev.userapi.domain.dto.UserDto;
import com.jcdev.userapi.domain.dto.UserToken;
import com.jcdev.userapi.domain.entity.AuthenticationToken;
import com.jcdev.userapi.domain.entity.Phone;
import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.domain.dto.UserResponse;
import com.jcdev.userapi.domain.service.AuthenticationTokenService;
import com.jcdev.userapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private String token;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationTokenService authenticationTokenService;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        token = "123456";
    }

    @Test
    void create() {
        List<PhoneDto> phonesDto = Arrays.asList(new PhoneDto("123456789", "11", "123"), new PhoneDto("987654321", "11", "321"));
        UserDto userDto = new UserDto("test user", "usertest@test.com", "123456", phonesDto);
        User user = userService.mapUser(userDto);
        user.setId(UUID.randomUUID());


        when(userRepository.save(any(User.class))).thenReturn(user);
        when(authenticationTokenService.create(any(User.class))).thenReturn(new AuthenticationToken(user, token));
        UserResponse result = userService.create(userDto);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getPhones(), result.getPhones());


    }

    @Test
    void signIn() {
        UserDto userDto = new UserDto("test user", "usertest@test.com", token, new ArrayList<>());
        User user = new User(userDto.getName(), userDto.getEmail(), userService.hashPassword(userDto.getPassword()), new ArrayList<>());
        UserToken expectedToken = new UserToken(token);

        when(userService.findByEmail(userDto.getEmail())).thenReturn(user);
        when(authenticationTokenService.create(user)).thenReturn(new AuthenticationToken(user, expectedToken.getToken()));

        UserToken result = userService.signIn(userDto);
        assertEquals(expectedToken.getToken(), result.getToken());
    }

    @Test
    void tokenCreation() {
    }

    @Test
    void mapUserResponse() {
    }
}