package com.jcdev.userapi.domain.service.implementation;

import com.jcdev.userapi.domain.entity.AuthenticationToken;
import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.domain.model.UserResponse;
import com.jcdev.userapi.domain.service.AuthenticationTokenService;
import com.jcdev.userapi.domain.service.UserService;
import com.jcdev.userapi.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;

    @Autowired
    @Qualifier("authenticationTokenService")
    private AuthenticationTokenService authenticationTokenService;
    
    @Override
    public UserResponse create(User user) {
        if (isEmailAlreadyInUse(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setIsActive(true);
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        try {
           User savedUser = userRepository.save(user);
           return tokenCreation(savedUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User setInactive(User user) {
        user.setIsActive(false);
        return userRepository.save(user);
    }

    boolean isEmailAlreadyInUse(String email) {
        return this.findByEmail(email) != null;
    }

    UserResponse tokenCreation(User user) {
        AuthenticationToken authToken = authenticationTokenService.create(user);
        return mapUserResponse(user, authToken.getToken());
    }

    public boolean validatePassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    UserResponse mapUserResponse(User user, String token) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhones(),
                user.getCreated(),
                user.getModified(),
                user.getLastLogin(),
                user.getIsActive(),
                token
        );
    }
}
