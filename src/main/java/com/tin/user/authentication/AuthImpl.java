package com.tin.user.authentication;

import com.tin.user.dto.CreateUserDto;
import com.tin.user.dto.UserResponseDto;
import com.tin.user.models.User;
import com.tin.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AuthImpl implements AuthenticationService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public UserResponseDto login(String email, String password) throws Exception {
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("Invalid email"));
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return convert(user);
    }

    @Override
    public UserResponseDto signup(CreateUserDto createUserDto) throws Exception {
        if (this.userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new Exception("user already exists");
        }
        String hashedPass = this.bCryptPasswordEncoder.encode(createUserDto.getPassword());
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setPassword(hashedPass);
        user.setUsername(createUserDto.getUsername());
        userRepository.save(user);
        return convert(user);
    }

    UserResponseDto convert(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }
}
