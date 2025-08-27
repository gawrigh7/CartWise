// src/main/java/com/cartwise/cartwise/mapper/RecipeSuggestionMapper.java
package com.cartwise.cartwise.mapper;

import com.cartwise.cartwise.dto.RecipeSuggestionCreateDto;
import com.cartwise.cartwise.dto.RecipeSuggestionDto;
import com.cartwise.cartwise.dto.RecipeSuggestionUpdateDto;
import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.repository.GroceryItemRepo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RecipeSuggestionMapper {

    private final GroceryItemRepo groceryItemRepo;

    public RecipeSuggestionMapper(GroceryItemRepo groceryItemRepo) {
        this.groceryItemRepo = groceryItemRepo;
    }

    public RecipeSuggestionDto toDto(RecipeSuggestion e) {
        if (e == null) return null;
        RecipeSuggestionDto dto = new RecipeSuggestionDto();
        dto.setId(e.getRecipeId());
        dto.setTitle(e.getTitle());
        dto.setDescription(e.getDescription());

        if (e.getIngredients() != null) {
            List<RecipeSuggestionDto.IngredientDto> minis = e.getIngredients().stream()
                    .map(gi -> new RecipeSuggestionDto.IngredientDto(gi.getGroceryId(), gi.getName()))
                    .toList();
            dto.setIngredients(minis);
        } else {
            dto.setIngredients(List.of());
        }
        return dto;
    }

    public RecipeSuggestion fromCreateDto(RecipeSuggestionCreateDto d) {
        RecipeSuggestion e = new RecipeSuggestion();
        e.setTitle(d.getTitle());
        e.setDescription(d.getDescription());
        if (d.getIngredientIds() != null && !d.getIngredientIds().isEmpty()) {
            List<GroceryItem> ingredients = d.getIngredientIds().stream()
                    .map(groceryItemRepo::getReferenceById)
                    .toList();
            e.setIngredients(new ArrayList<>(ingredients));
        } else {
            e.setIngredients(new ArrayList<>());
        }
        return e;
    }

    public void applyUpdate(RecipeSuggestion e, RecipeSuggestionUpdateDto d) {
        e.setTitle(d.getTitle());
        e.setDescription(d.getDescription());
        if (d.getIngredientIds() != null) {
            List<GroceryItem> ingredients = d.getIngredientIds().stream()
                    .map(groceryItemRepo::getReferenceById)
                    .toList();
            e.setIngredients(new ArrayList<>(ingredients));
        }
    }
}

