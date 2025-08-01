package com.cartwise.cartwise.controller;


import com.cartwise.cartwise.model.Users;
import com.cartwise.cartwise.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UsersController {

    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public Users registerUser(@RequestBody Users users) {
        return userService.registerUser(users);
    }

}
