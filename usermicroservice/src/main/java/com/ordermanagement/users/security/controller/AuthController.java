package com.ordermanagement.users.security.controller;

import com.ordermanagement.users.security.model.UserDetailsImpl;
import com.ordermanagement.users.security.service.JwtService;
import com.ordermanagement.users.util.Constant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authManager;

    @Autowired
    public AuthController(JwtService jwtService, AuthenticationManager authManager) {
        this.jwtService = jwtService;
        this.authManager = authManager;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();

        try {
            Authentication authentication = authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.get("username"), request.get("password"))
            );

            UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
            String token = jwtService.generateToken(user.getUsername());
            String refreshToken = jwtService.generateRefreshToken(user.getUsername());

            response.put("access_token", token);
            response.put("refresh_token", refreshToken);
            response.put("userId", user.getId());
            response.put("name", user.getFirstName()+" "+user.getLastName());
            response.put("email", user.getUsername());
            response.put("roleId", user.getRoleId());
            response.put("contact", user.getContactNumber());
            response.put("departmentId", user.getDepartmentId());
            response.put("managerId", user.getManagerId());

            System.out.println("---------Login Successful-------");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message",e.getMessage());
            response.put("status", Constant.RESPONSE_CODE.UNAUTHORIZED);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody Map<String, String> request) {
        String refreshToken = request.get("refreshToken");

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("message", "Refresh token is required"));
        }

        String username = jwtService.extractUsername(refreshToken);

        if (jwtService.isTokenValid(refreshToken, username)) {
            String newAccessToken = jwtService.generateToken(username);

            Map<String, String> response = new HashMap<>();
            response.put("access_token", newAccessToken);

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(Constant.RESPONSE_CODE.UNAUTHORIZED).body(Map.of("message", "Invalid or expired refresh token"));
        }
    }

}
