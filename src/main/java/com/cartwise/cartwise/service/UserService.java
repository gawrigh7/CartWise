package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.model.User;
import com.cartwise.cartwise.model.UsersPrinciple;
import com.cartwise.cartwise.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepo userRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    public List<RecipeSuggestion> getFavoritesForUser(Long userId) {
        return userRepo.findFavoriteRecipesByUsersId(userId);
    }

    public User registerUser(User user) {
        if (userRepo.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Transactional
    public void changePassword(Long userId, String newPassword) {
        User user = getByUserId(userId);
        user.setPassword(encoder.encode(newPassword));
        userRepo.save(user);
    }

    @Transactional
    public void deleteUser(Long userId) {
        userRepo.deleteById(userId);
    }

    @Transactional
    public void changeUsername(Long userId, String newUsername) {
        User user = getByUserId(userId);
        if (userRepo.existsByUsername(newUsername)) {
            throw new RuntimeException("Username already taken");
        }
        user.setUsername(newUsername);
        userRepo.save(user);
    }

    public User getByUserId(Long userId) {
        if (userRepo.findByUsersId(userId) == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return userRepo.findByUsersId(userId);
    }

    public boolean existsByUsername(String username) {
        return userRepo.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepo.existsByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UsersPrinciple(user);
    }
}
