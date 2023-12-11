package com.jcdev.userapi.domain.service.implementation;

import com.jcdev.userapi.domain.dto.PhoneDto;
import com.jcdev.userapi.domain.dto.UserDto;
import com.jcdev.userapi.domain.entity.AuthenticationToken;
import com.jcdev.userapi.domain.entity.Phone;
import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.domain.exception.EmailAlreadyExistsException;
import com.jcdev.userapi.domain.exception.EmailNotFoundException;
import com.jcdev.userapi.domain.exception.InvalidPasswordException;
import com.jcdev.userapi.domain.dto.UserResponse;
import com.jcdev.userapi.domain.dto.UserToken;
import com.jcdev.userapi.domain.service.AuthenticationTokenService;
import com.jcdev.userapi.domain.service.UserService;
import com.jcdev.userapi.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;


    @Autowired
    @Qualifier("authenticationTokenService")
    private AuthenticationTokenService authenticationTokenService;
    
    @Override
    public UserResponse create(UserDto userDto) {
        User user = mapUser(userDto);
        isEmailAlreadyInUse(user.getEmail());
        user.setIsActive(true);
        user.setPassword(hashPassword(user.getPassword()));
        try {
           User savedUser = userRepository.save(user);
           return tokenCreation(savedUser);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    @Override
    public UserToken signIn(UserDto userDto) {
        User user = mapUser(userDto);
        User userFound = this.findByEmail(user.getEmail());
        if (userFound == null) throw new EmailNotFoundException("Email not found");
        if (!validatePassword(user.getPassword(), userFound.getPassword())) throw new InvalidPasswordException("Invalid password");
        UserToken token = new UserToken(tokenCreation(userFound).getToken());

        return token;
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

    void isEmailAlreadyInUse(String email) {
        if (this.findByEmail(email) != null) throw new EmailAlreadyExistsException("Email already in use");
    }

    UserResponse tokenCreation(User user) {
        AuthenticationToken authToken = authenticationTokenService.create(user);
        return mapUserResponse(user, authToken.getToken());
    }

    public boolean validatePassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    User mapUser(UserDto userDto) {
        List<Phone> phones = userDto.getPhones() != null ?
                userDto.getPhones().stream().map(this::mapPhone).collect(Collectors.toList()) :
                new ArrayList<>();

        return new User(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getPassword(),
                phones
        );
    }

    Phone mapPhone(PhoneDto phoneDto) {
        return new Phone(
                phoneDto.getNumber(),
                phoneDto.getCitycode(),
                phoneDto.getCountrycode()
        );
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
