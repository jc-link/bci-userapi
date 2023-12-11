package com.jcdev.userapi.controller;

import com.jcdev.userapi.domain.dto.UserDto;
import com.jcdev.userapi.domain.entity.User;
import com.jcdev.userapi.domain.dto.UserResponse;
import com.jcdev.userapi.domain.dto.UserToken;
import com.jcdev.userapi.domain.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/user/auth")
public class UserController {

    @Autowired
    @Qualifier("userService")
    private UserService userService;

    @ApiOperation(value = "Sign up", notes = "Sign up with name, email, password and phones")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "", response = UserResponse.class),
            @ApiResponse(code = 400, message = "Email already exists"),
            @ApiResponse(code = 400, message = "Invalid password")
    })
    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@Valid @RequestBody UserDto userDto) {
        UserResponse serviceResponse = userService.create(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceResponse);
    }

    @ApiOperation(value = "Sign in", notes = "Sign in with email and password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "", response = UserToken.class),
            @ApiResponse(code = 400, message = "Email not found"),
            @ApiResponse(code = 400, message = "Invalid password")
    })
    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signIn(@RequestBody UserDto userDto) {
        UserToken token = userService.signIn(userDto);
        return ResponseEntity.ok(token);
    }

}
