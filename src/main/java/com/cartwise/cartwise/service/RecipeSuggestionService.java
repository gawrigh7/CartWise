package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.model.User;
import com.cartwise.cartwise.repository.RecipeSuggestionRepo;
import com.cartwise.cartwise.repository.UserRepo;
import com.cartwise.cartwise.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeSuggestionService {

    private final RecipeSuggestionRepo recipeRepo;
    private final UserRepo userRepo;

    public RecipeSuggestionService(RecipeSuggestionRepo recipeRepo, UserRepo userRepo) {
        this.recipeRepo = recipeRepo;
        this.userRepo = userRepo;
    }

    @Transactional(readOnly = true)
    public Optional<RecipeSuggestion> findRecipeSuggestionById(Long id) {
        Long userId = SecurityUtil.getCurrentUserId();
        return recipeRepo.findByUserUserIdAndRecipeId(userId, id);
    }

    @Transactional(readOnly = true)
    public List<RecipeSuggestion> findAllByUser() {
        Long userId = SecurityUtil.getCurrentUserId();
        return recipeRepo.findFavoriteRecipesByUserUserId(userId);
    }

    @Transactional
    public RecipeSuggestion saveRecipeSuggestion(RecipeSuggestion recipeSuggestion) {
        Long userId = SecurityUtil.getCurrentUserId();
        User ownerRef = userRepo.getReferenceById(userId);
        recipeSuggestion.setUser(ownerRef);
        return recipeRepo.save(recipeSuggestion);
    }

    @Transactional
    public void deleteForCurrentUser(Long recipeId) {
        Long userId = SecurityUtil.getCurrentUserId();
        recipeRepo.deleteByRecipeIdAndUserUserId(recipeId, userId);
    }
}
