package com.jcdev.userapi.domain.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;

public class UserDto {

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @NotEmpty(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+\\..+)$", message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d.*\\d).+$", message = "Password must contain at least one uppercase letter, one lowercase letter, and two digits")
    private String password;

    private List<PhoneDto> phones;

    public UserDto() {
    }
    public UserDto(String name, String email, String password, List<PhoneDto> phones) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phones = phones;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PhoneDto> getPhones() {
        return phones;
    }

    public void setPhones(List<PhoneDto> phones) {
        this.phones = phones;
    }
}
