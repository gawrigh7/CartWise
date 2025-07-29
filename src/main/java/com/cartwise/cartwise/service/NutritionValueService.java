package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.NutritionValue;
import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.repository.NutritionValueRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NutritionValueService {

    private final NutritionValueRepo nutritionValueRepo;

    public NutritionValueService(NutritionValueRepo nutritionValueRepo) {
        this.nutritionValueRepo = nutritionValueRepo;
    }

    public Optional<NutritionValue> findByRecipeSuggestion(RecipeSuggestion recipeSuggestion) {
        return nutritionValueRepo.findByRecipeSuggestion(recipeSuggestion);
    }
}
