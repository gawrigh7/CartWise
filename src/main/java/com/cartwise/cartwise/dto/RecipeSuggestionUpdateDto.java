package com.cartwise.cartwise.dto;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

public class RecipeSuggestionUpdateDto {
    @NotBlank private String title;
    private String description;
    private List<Long> ingredientIds; // optional

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<Long> getIngredientIds() { return ingredientIds; }
    public void setIngredientIds(List<Long> ingredientIds) { this.ingredientIds = ingredientIds; }
}

