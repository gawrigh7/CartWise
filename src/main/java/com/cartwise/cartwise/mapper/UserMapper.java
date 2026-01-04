package com.cartwise.cartwise.mapper;

import com.cartwise.cartwise.dto.UserResponseDto;
import com.cartwise.cartwise.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto toDto(User u) {
        if (u == null) return null;
        return new UserResponseDto(u.getUsersId(), u.getUsername(), u.getEmail());
    }
}
