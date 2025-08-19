package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.model.Users;
import com.cartwise.cartwise.model.UsersPrinciple;
import com.cartwise.cartwise.repository.UsersRepo;
import jakarta.transaction.Transactional;
import org.hibernate.validator.internal.constraintvalidators.bv.time.future.FutureValidatorForInstant;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UsersRepo usersRepo;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public UserService(UsersRepo usersRepo) {
        this.usersRepo = usersRepo;
    }

    public List<RecipeSuggestion> getFavoritesForUser(Long userId) {
        return usersRepo.findFavoriteRecipesByUsersId(userId);
    }

    public Users registerUser(Users user) {
        if (usersRepo.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("Username already taken");
        }
        if (usersRepo.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        return usersRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = usersRepo.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UsersPrinciple(user);
    }

    @Transactional
    public void changePassword(String username, String newPassword) {
        Users user = getByUsername(username);
        user.setPassword(encoder.encode(newPassword));
        usersRepo.save(user);
    }

    @Transactional
    public void deleteUser(Users user) {
        usersRepo.delete(user);
    }

    public void changeUsername(String username, String newUsername) {
        Users user = getByUsername(username);
        if (usersRepo.existsByUsername(newUsername)) {
            throw new RuntimeException("Username already taken");
        }
        user.setUsername(newUsername);
        usersRepo.save(user);
    }

    public Users getByEmail(String email) {
        if (usersRepo.findByEmail(email) == null) {
            throw new UsernameNotFoundException(email);
        }
        return usersRepo.findByEmail(email);
    }

    public Users getByUsername(String username) {
        if (usersRepo.findByUsername(username) == null) {
            throw new UsernameNotFoundException(username);
        }
        return usersRepo.findByUsername(username);
    }

    public boolean existsByUsername(String username) {
        return usersRepo.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return usersRepo.existsByEmail(email);
    }
}
