package com.cartwise.cartwise.repository;

import com.cartwise.cartwise.model.RecipeSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeSuggestionRepo extends JpaRepository<RecipeSuggestion, Long> {

    Optional<RecipeSuggestion> findByUserUserIdAndRecipeId(Long userId, Long recipeId);

    long deleteByUserUserIdAndRecipeId(Long userId, Long recipeId);

    @Query("SELECT u.favoriteRecipes FROM User u WHERE u.userId = :userId")
    List<RecipeSuggestion> findFavoriteRecipesByUserUserId(@Param("userId") Long userId);
}
