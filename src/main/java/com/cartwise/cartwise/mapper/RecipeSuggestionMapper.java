package com.cartwise.cartwise.mapper;

import com.cartwise.cartwise.dto.RecipeSuggestionDto;
import com.cartwise.cartwise.model.RecipeSuggestion;
import org.springframework.stereotype.Component;

@Component
public class RecipeSuggestionMapper {

    public RecipeSuggestionDto toRecipeDto(RecipeSuggestion recipeSuggestion) {
        if (recipeSuggestion == null) return null;
        return new RecipeSuggestionDto(
                recipeSuggestion.getRecipeId(),
                recipeSuggestion.getTitle(),
                recipeSuggestion.getDescription()
        );
    }

    public RecipeSuggestion fromRecipeCreateDto(RecipeSuggestionDto recipeSuggestionDto) {
        if (recipeSuggestionDto == null) return null;
        RecipeSuggestion recipeSuggestion = new RecipeSuggestion();
        recipeSuggestion.setTitle(recipeSuggestionDto.getTitle());
        recipeSuggestion.setDescription(recipeSuggestionDto.getDescription());
        return recipeSuggestion;
    }
}
