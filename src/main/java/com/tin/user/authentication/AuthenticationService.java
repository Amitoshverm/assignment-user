package com.tin.user.authentication;

import com.tin.user.dto.CreateUserDto;
import com.tin.user.dto.LoginUserDto;
import com.tin.user.dto.UserResponseDto;
import com.tin.user.models.Session;
import com.tin.user.models.SessionStatus;
import com.tin.user.models.User;
import com.tin.user.repository.SessionRepository;
import com.tin.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMapAdapter;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthenticationService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private TokenGenerator tokenGenerator;
    private final SessionRepository sessionRepository;

    public AuthenticationService(UserRepository userRepository,
                                 BCryptPasswordEncoder bCryptPasswordEncoder,

                                 TokenGenerator tokenGenerator,
                                 SessionRepository sessionRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;

        this.tokenGenerator = tokenGenerator;
        this.sessionRepository = sessionRepository;
    }

    public UserResponseDto login(LoginUserDto loginUserDto) throws Exception {
        Optional<User> userOptional = this.userRepository.findByEmail(loginUserDto.getEmail());
        if (userOptional.isEmpty()) {
            return null;
        }

        User user = userOptional.get();

        if (!bCryptPasswordEncoder.matches(loginUserDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        String token = tokenGenerator.generate();
        Session session = new Session();
        session.setToken(token);
        session.setUser(user);
        session.setStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        return convert(user);
    }

    @Transactional
    public String signup(CreateUserDto createUserDto) throws Exception {
        if (this.userRepository.existsByEmail(createUserDto.getEmail())) {
            throw new Exception("user already exists");
        }
        String hashedPass = this.bCryptPasswordEncoder.encode(createUserDto.getPassword());
        User user = new User();
        user.setEmail(createUserDto.getEmail());
        user.setPassword(hashedPass);
        user.setUsername(createUserDto.getUsername());
        userRepository.save(user);
//
        Session session = new Session();
        String token = tokenGenerator.generate();
        session.setToken(token);
        session.setUser(user);
        session.setStatus(SessionStatus.ACTIVE);
        sessionRepository.save(session);

        MultiValueMapAdapter<String, String> headers = new MultiValueMapAdapter<>(new HashMap<>());
        headers.add(HttpHeaders.SET_COOKIE, "auth-token:" + token);

        return token;
    }

    public ResponseEntity<Void> logout(String token, Long userId) {
        Optional<Session> sessionOptional = this.sessionRepository.findByTokenAndUserId(token, userId);
        if (sessionOptional.isEmpty()) {
            return null;
        }
        Session session = sessionOptional.get();
        session.setStatus(SessionStatus.INACTIVE);
        sessionRepository.save(session);
        return ResponseEntity.ok().build();
    }

    UserResponseDto convert(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setName(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }


}
