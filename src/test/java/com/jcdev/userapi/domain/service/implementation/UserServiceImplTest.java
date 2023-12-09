package com.jcdev.userapi.domain.service.implementation;

import com.jcdev.userapi.domain.entity.AuthenticationToken;
import com.jcdev.userapi.domain.entity.Phone;
import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.domain.model.UserResponse;
import com.jcdev.userapi.domain.service.AuthenticationTokenService;
import com.jcdev.userapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationTokenService authenticationTokenService;

    @InjectMocks
    private UserServiceImpl userService;
    @Test
    void create() {
        User user = new User("test user", "usertest@test.com", "123456");
        List<Phone> phones = Arrays.asList(new Phone("123456789", "11", "123"), new Phone("987654321", "11", "321"));
        user.setPhones(phones);
        user.setId(UUID.randomUUID());
        String token = "123456";

        when(userRepository.save(user)).thenReturn(user);
        when(authenticationTokenService.create(user)).thenReturn(new AuthenticationToken(user, token));
        UserResponse result = userService.create(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getPhones(), result.getPhones());


    }

    @Test
    void tokenCreation() {
    }

    @Test
    void mapUserResponse() {
    }
}