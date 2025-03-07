package com.example.GreetingApp.Controller;

import com.example.GreetingApp.Dto.AuthUserDTO;
import com.example.GreetingApp.Dto.LoginDTO;
import com.example.GreetingApp.Service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthUserController {
//    @Autowired -yaha par feild injection error de raha tha to onstructor injection use kiya.kuch static non static ki dikkat thi.
//     AuthenticationService authenticationService;
    //removing private final --no need of it
    //private final AuthenticationService service;
    AuthenticationService service;

    public AuthUserController(AuthenticationService service) {

        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AuthUserDTO userDTO) {
        return ResponseEntity.ok(service.registerUser(userDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO) {
        return ResponseEntity.ok(service.loginUser(loginDTO));
    }
    @PutMapping("/forgotPassword/{email}")
    public ResponseEntity<String> forgotPassword(
            @PathVariable String email,
            @RequestBody Map<String, String> requestBody) {
        String newPassword = requestBody.get("password");
        String response = service.forgotPassword(email, newPassword);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/resetPassword/{email}")
    public ResponseEntity<String> resetPassword(
            @PathVariable String email,
            @RequestBody Map<String, String> requestBody) {
        String currentPassword = requestBody.get("currentPassword");
        String newPassword = requestBody.get("newPassword");
        String response = service.resetPassword(email, currentPassword, newPassword);
        return ResponseEntity.ok(response);
    }


}

