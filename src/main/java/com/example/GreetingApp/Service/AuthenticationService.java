package com.example.GreetingApp.Service;

import com.example.GreetingApp.Dto.AuthUserDTO;
import com.example.GreetingApp.Dto.LoginDTO;
import com.example.GreetingApp.Model.AuthUser;
import com.example.GreetingApp.Repository.AuthUserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    private final AuthUserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthenticationService(AuthUserRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String registerUser(AuthUserDTO userDTO) {
        if (repository.findByEmail(userDTO.getEmail()).isPresent()) {
            return "Email is already in use";
        }

        AuthUser user = new AuthUser(null, userDTO.getFirstName(), userDTO.getLastName(),
                userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()));

        repository.save(user);
        return "User registered successfully!";
    }

    public String loginUser(LoginDTO loginDTO) {
        Optional<AuthUser> user = repository.findByEmail(loginDTO.getEmail());
        if (user.isEmpty()) {
            return "User not found!";
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
            return "Invalid email or password!";
        }

        return "Login successful!";
    }
}
