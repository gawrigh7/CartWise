package com.cartwise.cartwise.controller;


import com.cartwise.cartwise.dto.PasswordChangeRequest;
import com.cartwise.cartwise.dto.UserRegisterRequest;
import com.cartwise.cartwise.dto.UserResponseDto;
import com.cartwise.cartwise.dto.UsernameChangeRequest;
import com.cartwise.cartwise.mapper.UserMapper;
import com.cartwise.cartwise.model.User;
import com.cartwise.cartwise.model.UsersPrinciple;
import com.cartwise.cartwise.service.JwtService;
import com.cartwise.cartwise.service.UserService;
import com.cartwise.cartwise.util.SecurityUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserMapper userMapper;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getCurrentUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userService.getByUserId(userId);
        return ResponseEntity.ok(userMapper.toDto(user));
    }

    @GetMapping("/exists/username/{username}")
    public ResponseEntity<Boolean> existsUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.existsByUsername(username));
    }

    @GetMapping("/exists/email/{email}")
    public ResponseEntity<Boolean> existsUserByEmail(@PathVariable String email) {
        return ResponseEntity.ok(userService.existsByEmail(email));
    }


    @PostMapping("/register")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserRegisterRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = userService.registerUser(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody User user) {
        Authentication auth =authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

        if (auth.isAuthenticated()) {
            UsersPrinciple principle = (UsersPrinciple) auth.getPrincipal();
            return ResponseEntity.ok(jwtService.generateToken(principle));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PutMapping("/me/password")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        userService.changePassword(userId, request.getNewPassword());
        return ResponseEntity.ok("Password changed");
    }

    @PutMapping("/me/username")
    public ResponseEntity<String> changeUsername(@RequestBody UsernameChangeRequest request) {
        Long userId = SecurityUtil.getCurrentUserId();
        userService.changeUsername(userId, request.getNewUsername());
        return ResponseEntity.ok("Username changed");
    }

    @DeleteMapping("/me/delete")
    public ResponseEntity<Void> deleteUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

}
