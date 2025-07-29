package com.cartwise.cartwise.repository;

import com.cartwise.cartwise.model.NutritionValue;
import com.cartwise.cartwise.model.RecipeSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NutritionValueRepo extends JpaRepository<NutritionValue, Long> {

    Optional<NutritionValue> findByRecipeSuggestion(RecipeSuggestion recipeSuggestion);
}
