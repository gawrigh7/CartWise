package com.cartwise.cartwise.repository;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.RecipeSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeSuggestionRepo extends JpaRepository<RecipeSuggestion, Long> {

    Optional<RecipeSuggestion> findByUserUsersIdAndTitle(Long userId, String title);
    Optional<RecipeSuggestion> findByUserUsersIdAndRecipeId(Long userId, Long recipeId);
    List<RecipeSuggestion> findAllByUserUsersId(Long userId);
    void deleteByUserUsersIdAndRecipeId(Long userId, Long recipeId);


}
