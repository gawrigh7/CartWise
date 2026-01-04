package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.model.User;
import com.cartwise.cartwise.repository.RecipeSuggestionRepo;
import com.cartwise.cartwise.repository.UserRepo;
import com.cartwise.cartwise.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeSuggestionService {

    private final RecipeSuggestionRepo recipeSuggestionRepo;
    private final UserRepo userRepo;

    public RecipeSuggestionService(RecipeSuggestionRepo recipeSuggestionRepo, UserRepo userRepo) {
        this.recipeSuggestionRepo = recipeSuggestionRepo;
        this.userRepo = userRepo;
    }

    public Optional<RecipeSuggestion> findRecipeSuggestionById(Long id) {
        Long userId = SecurityUtil.getCurrentUserId();
        return recipeSuggestionRepo.findByUserUsersIdAndRecipeId(userId, id);
    }

    public List<RecipeSuggestion> findAllByUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        return recipeSuggestionRepo.findAllByUserUsersId(userId);
    }

    public RecipeSuggestion saveRecipeSuggestion(RecipeSuggestion recipeSuggestion) {
        Long userId = SecurityUtil.getCurrentUserId();
        User user = userRepo.findById(userId).orElseThrow();
        recipeSuggestion.setUser(user);
        return recipeSuggestionRepo.save(recipeSuggestion);
    }

    @Transactional
    public void deleteForCurrentUser(Long recipeId) {
        Long userId = SecurityUtil.getCurrentUserId();
        recipeSuggestionRepo.deleteByUserUsersIdAndRecipeId(userId, recipeId);
    }

}
