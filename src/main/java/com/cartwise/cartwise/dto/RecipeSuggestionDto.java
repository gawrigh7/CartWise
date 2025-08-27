package com.cartwise.cartwise.dto;

import java.util.List;

public class RecipeSuggestionDto {
    private Long id;
    private String title;
    private String description;
    private List<IngredientDto> ingredients;

    public static class IngredientDto {
        private Long id;
        private String name;

        public IngredientDto() {}
        public IngredientDto(Long id, String name) { this.id = id; this.name = name; }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public List<IngredientDto> getIngredients() { return ingredients; }
    public void setIngredients(List<IngredientDto> ingredients) { this.ingredients = ingredients; }
}

