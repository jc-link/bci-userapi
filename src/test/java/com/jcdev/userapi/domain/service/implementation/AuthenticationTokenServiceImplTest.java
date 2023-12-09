package com.jcdev.userapi.domain.service.implementation;

import com.jcdev.userapi.domain.entity.AuthenticationToken;
import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.repository.AuthenticationTokenRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationTokenServiceImplTest {

    private User user;
    @Mock
    private AuthenticationTokenRepository authenticationTokenRepository;

    @InjectMocks
    private AuthenticationTokenServiceImpl authenticationTokenService;

    @BeforeEach
    void setUp() {
        user = new User("test user", "user@mail.com", "123456");
    }

    @Test
    void create() {
        AuthenticationToken authToken = new AuthenticationToken(user, "token");
        when(authenticationTokenRepository.save(any(AuthenticationToken.class))).thenReturn(authToken);

        AuthenticationToken result = authenticationTokenService.create(user);

        assertNotNull(result);
        assertEquals(authToken, result);

    }

    @Test
    void generateToken() {
    }

    @Test
    void createAuthenticationToken() {
        AuthenticationToken result = authenticationTokenService.createAuthenticationToken(user);
        assertEquals(user, result.getUser());
        assertNotNull(result.getToken());
    }
}