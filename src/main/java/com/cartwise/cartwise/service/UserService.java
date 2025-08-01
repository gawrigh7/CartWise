package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.model.Users;
import com.cartwise.cartwise.model.UsersPrinciple;
import com.cartwise.cartwise.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
}
