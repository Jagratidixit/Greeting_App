package com.example.GreetingApp.Service;

import com.example.GreetingApp.Dto.AuthUserDTO;
import com.example.GreetingApp.Dto.LoginDTO;
import com.example.GreetingApp.Model.AuthUser;
import com.example.GreetingApp.Repository.AuthUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    AuthUserRepository authUserRepository;
    PasswordEncoder passwordEncoder;
    EmailService emailService;

//    @Autowired cannot be used with static fields (authUserRepository, passwordEncoder).
//    forgotPassword is static, but it accesses non-static fields.
//    Constructor-based injection is preferred over field injection.

    @Autowired
    public AuthenticationService(AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public String registerUser(AuthUserDTO userDTO) {
        if (authUserRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            return "Email is already in use";
        }

        AuthUser user = new AuthUser();
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        authUserRepository.save(user);
        emailService.sendEmail(user.getEmail(), "Registration Successful",
                "Welcome " + user.getFirstName() + "! Your account has been created.");

        return "User registered successfully!";
    }

    public String loginUser(LoginDTO loginDTO) {
        Optional<AuthUser> user = authUserRepository.findByEmail(loginDTO.getEmail());
        if (user.isEmpty()) {
            return "User not found!";
        }

        if (!passwordEncoder.matches(loginDTO.getPassword(), user.get().getPassword())) {
            return "Invalid email or password!";
        }

        return "Login successful!";
    }

    // UC12: Forgot Password
    public String forgotPassword(String email, String newPassword) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "Sorry! We cannot find the user email: " + email;
        }

        AuthUser user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        authUserRepository.save(user);

        //this extra line of code to send email if password changed
        try {
            emailService.sendEmail(user.getEmail(), "Password Reset Successful", "Your password has been updated.");
            System.out.println("Email sent successfully to: " + user.getEmail());
        } catch (Exception e) {
            e.printStackTrace();
            return "Password changed, but email sending failed!";
        }

        return "Password has been changed successfully!";
    }
    // UC13: Reset Password
    public String resetPassword(String email, String currentPassword, String newPassword) {
        Optional<AuthUser> userOptional = authUserRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            return "User not found with email: " + email;
        }

        AuthUser user = userOptional.get();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return "Current password is incorrect!";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        authUserRepository.save(user);

        return "Password reset successfully!";
    }


}
