package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.repository.UserRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepo userRepo;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<RecipeSuggestion> getFavoritesForUser(Long userId) {
        return userRepo.findFavoriteRecipesByUserId(userId);
    }

}
