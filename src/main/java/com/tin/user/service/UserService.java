package com.tin.user.service;

import com.tin.user.dto.CreateUserDto;
import com.tin.user.dto.UserResponseDto;
import com.tin.user.models.User;
import com.tin.user.repository.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    UserResponseDto createUser(CreateUserDto createUserDto) {
        User user = new User();
        user.setUsername(createUserDto.getUsername());
        user.setEmail(createUserDto.getEmail());
        user.setPassword(createUserDto.getPassword());

        userRepository.save(user);
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setName(user.getUsername());
        return userResponseDto;
    }
}
