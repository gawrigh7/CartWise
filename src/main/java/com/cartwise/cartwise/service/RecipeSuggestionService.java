package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.model.User;
import com.cartwise.cartwise.repository.RecipeSuggestionRepo;
import com.cartwise.cartwise.repository.UserRepo;
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
        return recipeSuggestionRepo.findById(id);
    }

    public List<RecipeSuggestion> findAllRecipeSuggestions() {
        return recipeSuggestionRepo.findAll();
    }

    public RecipeSuggestion saveRecipeSuggestion(RecipeSuggestion recipeSuggestion, Long userId) {
        User user = userRepo.findById(userId).orElseThrow();
        user.getFavoriteRecipes().add(recipeSuggestion);
        userRepo.save(user);
        return recipeSuggestion;
    }

    public List<RecipeSuggestion> findAllByUser(Long userId) {
        return userRepo.findFavoriteRecipesByUserId(userId);
    }


    @Transactional
    public void deleteRecipeSuggestion(RecipeSuggestion recipeSuggestion) {
        recipeSuggestionRepo.delete(recipeSuggestion);
    }

    @Transactional
    public void unfavoriteRecipe(Long recipeId, Long userId) {
        RecipeSuggestion recipe = recipeSuggestionRepo.findById(recipeId).orElseThrow();
        User user = userRepo.findById(userId).orElseThrow();
        user.getFavoriteRecipes().remove(recipe);
        userRepo.save(user);
    }




}
