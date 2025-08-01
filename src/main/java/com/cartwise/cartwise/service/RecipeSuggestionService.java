package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.model.Users;
import com.cartwise.cartwise.repository.RecipeSuggestionRepo;
import com.cartwise.cartwise.repository.UsersRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeSuggestionService {

    private final RecipeSuggestionRepo recipeSuggestionRepo;
    private final UsersRepo usersRepo;

    public RecipeSuggestionService(RecipeSuggestionRepo recipeSuggestionRepo, UsersRepo usersRepo) {
        this.recipeSuggestionRepo = recipeSuggestionRepo;
        this.usersRepo = usersRepo;
    }

    public Optional<RecipeSuggestion> findRecipeSuggestionById(Long id) {
        return recipeSuggestionRepo.findById(id);
    }

    public List<RecipeSuggestion> findAllRecipeSuggestions() {
        return recipeSuggestionRepo.findAll();
    }

    public RecipeSuggestion saveRecipeSuggestion(RecipeSuggestion recipeSuggestion, Long userId) {
        Users users = usersRepo.findById(userId).orElseThrow();
        users.getFavoriteRecipes().add(recipeSuggestion);
        usersRepo.save(users);
        return recipeSuggestion;
    }

    public List<RecipeSuggestion> findAllByUser(Long userId) {
        return usersRepo.findFavoriteRecipesByUsersId(userId);
    }


    @Transactional
    public void deleteRecipeSuggestion(RecipeSuggestion recipeSuggestion) {
        recipeSuggestionRepo.delete(recipeSuggestion);
    }

    @Transactional
    public void unfavoriteRecipe(Long recipeId, Long userId) {
        RecipeSuggestion recipe = recipeSuggestionRepo.findById(recipeId).orElseThrow();
        Users users = usersRepo.findById(userId).orElseThrow();
        users.getFavoriteRecipes().remove(recipe);
        usersRepo.save(users);
    }




}
