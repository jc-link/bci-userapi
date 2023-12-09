package com.jcdev.userapi.controller;

import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.domain.model.UserResponse;
import com.jcdev.userapi.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user/auth")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody User user) {
        UserResponse serviceResponse = userService.create(user);
        return ResponseEntity.ok(serviceResponse);
    }

    @GetMapping(path = "/signin", produces = MediaType.APPLICATION_JSON_VALUE)
    public String signIn() {
        return "Sign In";
    }

}
