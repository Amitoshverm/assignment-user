package com.tin.user.authentication;

import com.tin.user.dto.CreateUserDto;
import com.tin.user.dto.UserResponseDto;

public interface AuthenticationService {
    String signup(CreateUserDto createUserDto) throws Exception;
    UserResponseDto login(String email, String password) throws Exception;
}
