package com.tin.user.controller;

import com.tin.user.authentication.AuthenticationService;
import com.tin.user.dto.CreateUserDto;
import com.tin.user.dto.LoginUserDto;
import com.tin.user.dto.LogoutDto;
import com.tin.user.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    private AuthenticationService authenticationService;

    public UserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public String signUp(@RequestBody CreateUserDto createUserDto) throws Exception {
        return this.authenticationService.signup(createUserDto);
    }

    @PostMapping("/login")
    public UserResponseDto login(@RequestBody LoginUserDto loginUserDto) throws Exception {
        return this.authenticationService.login(loginUserDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutDto logoutDto) throws Exception {
        return this.authenticationService.logout(logoutDto.getToken(), logoutDto.getUserId());
    }
}
