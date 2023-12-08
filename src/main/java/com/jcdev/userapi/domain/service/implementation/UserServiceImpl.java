package com.jcdev.userapi.domain.service.implementation;

import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.domain.service.UserService;
import com.jcdev.userapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    
    @Override
    public User create(User user) {
        if (isEmailAlreadyInUse(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        return userRepository.save(user);
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
        user.setActive(false);
        return userRepository.save(user);
    }

    boolean isEmailAlreadyInUse(String email) {
        return this.findByEmail(email) != null;
    }
}
