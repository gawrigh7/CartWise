package com.cartwise.cartwise.mapper;

import com.cartwise.cartwise.dto.UserCreateDto;
import com.cartwise.cartwise.dto.UserDto;
import com.cartwise.cartwise.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        if (user == null) return null;
        UserDto dto = new UserDto();
        dto.setId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        return dto;
    }

    public User fromCreateDto(UserCreateDto req) {
        if (req == null) return null;
        User u = new User();
        u.setUsername(req.getUsername());
        u.setEmail(req.getEmail());
        u.setPassword(req.getPassword());
        return u;
    }
}
