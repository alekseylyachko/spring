package com.spring.rest;

import com.spring.dto.AuthenticationRequestDto;
import com.spring.model.User;
import com.spring.security.jwt.JwtTokenProvider;
import com.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    @Autowired
    public AuthenticationRestControllerV1(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("signup")
    public ResponseEntity signup(@RequestBody AuthenticationRequestDto requestDto) {
        String username = requestDto.getUsername();
        User user = new User();
        user.setUsername(username);
        user.setPassword(requestDto.getPassword());

        user =  userService.register(user, "ROLE_USER");

        if (user == null) {
            throw new UsernameNotFoundException("Failed to create user with username " + username);
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

        String token = jwtTokenProvider.createToken(username, user.getRoles());

        Map<Object, Object> response = new HashMap<>();
        response.put("username", username);
        response.put("token", token);

        return ResponseEntity.ok(response);
    }

    @PostMapping("auth/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto) {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);

            return ResponseEntity.ok(response);
    }
}
