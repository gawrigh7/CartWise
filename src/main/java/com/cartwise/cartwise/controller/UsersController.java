package com.cartwise.cartwise.controller;


import com.cartwise.cartwise.dto.PasswordChangeRequest;
import com.cartwise.cartwise.dto.UserRegisterRequest;
import com.cartwise.cartwise.dto.UsernameChangeRequest;
import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.Users;
import com.cartwise.cartwise.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{username}")
    public ResponseEntity<Users> getUserByUsername(@PathVariable String username) {
        Users user = userService.getByUsername(username);

        return ResponseEntity.ok(user);
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
        Users user = new Users();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        Users savedUser = userService.registerUser(user);
        return ResponseEntity.status(201).body(savedUser);
    }

    @PutMapping("/{username}/password")
    public ResponseEntity<String> changePassword(@PathVariable String username, @RequestBody PasswordChangeRequest request) {
        userService.changePassword(username, request.getNewPassword());
        return ResponseEntity.ok("Password changed");
    }

    @PutMapping("/{username}/username")
    public ResponseEntity<String> changeUsername(@PathVariable String username, @RequestBody UsernameChangeRequest request) {
        userService.changeUsername(username, request.getNewUsername());
        return ResponseEntity.ok("Username changed");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable String username) {
        Users deletedUser = userService.getByUsername(username);
        if (deletedUser != null) {
            userService.deleteUser(deletedUser);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
