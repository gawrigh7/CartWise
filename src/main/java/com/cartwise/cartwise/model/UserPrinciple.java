package com.cartwise.cartwise.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserPrinciple implements UserDetails {

    private User user;

    public UserPrinciple(User user) { this.user = user; }

    public static UserPrinciple fromJwt(Long id, String username) {
        User u = new User();
        u.setUserId(id);
        u.setUsername(username);
        return new UserPrinciple(u);
    }

    public Long getId() { return user.getUserId(); }

    @Override public String getUsername() { return user.getUsername(); }
    @Override public String getPassword() { return user.getPassword(); }
    @Override public Collection<? extends GrantedAuthority> getAuthorities() { return List.of(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

