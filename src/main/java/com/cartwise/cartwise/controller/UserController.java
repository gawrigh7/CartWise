package com.cartwise.cartwise.controller;

import com.cartwise.cartwise.dto.PasswordChangeRequest;
import com.cartwise.cartwise.dto.UserCreateDto;
import com.cartwise.cartwise.dto.UserDto;
import com.cartwise.cartwise.dto.UsernameChangeRequest;
import com.cartwise.cartwise.mapper.UserMapper;
import com.cartwise.cartwise.model.User;
import com.cartwise.cartwise.model.UserPrinciple;
import com.cartwise.cartwise.service.JwtService;
import com.cartwise.cartwise.service.UserService;
import com.cartwise.cartwise.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    public UserController(UserService userService,
                           AuthenticationManager authenticationManager,
                           JwtService jwtService,
                           UserMapper userMapper) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userMapper = userMapper;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userService.getByUserId(userId);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserCreateDto request) {
        User toSave = userMapper.fromCreateDto(request);
        User saved = userService.registerUser(toSave);
        return ResponseEntity.status(201).body(userMapper.toDto(saved));
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (auth.isAuthenticated()) {
            var p = (UserPrinciple) auth.getPrincipal();
            String token = jwtService.generateToken(p.getId(), p.getUsername());
            return ResponseEntity.ok(token);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<String> changePassword(@RequestBody @Valid PasswordChangeRequest newPassword) {
        Long userId = SecurityUtil.getCurrentUserId();
        userService.changePassword(userId, newPassword.getNewPassword());
        return ResponseEntity.ok("Password changed");
    }

    @PutMapping("/me/username")
    public ResponseEntity<String> changeUsername(@RequestBody @Valid UsernameChangeRequest newUsername) {
        Long userId = SecurityUtil.getCurrentUserId();
        userService.changeUsername(userId, newUsername.getNewUsername());
        return ResponseEntity.ok("Username changed");
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> existsUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.existsByUsername(username));
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }
}
