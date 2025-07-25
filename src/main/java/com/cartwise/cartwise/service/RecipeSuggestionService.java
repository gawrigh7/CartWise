package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.repository.RecipeSuggestionRepo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeSuggestionService {

    private final RecipeSuggestionRepo recipeSuggestionRepo;

    public RecipeSuggestionService(RecipeSuggestionRepo recipeSuggestionRepo) {
        this.recipeSuggestionRepo = recipeSuggestionRepo;
    }

    public Optional<RecipeSuggestion> findRecipeSuggestionById(Long id) {
        return recipeSuggestionRepo.findById(id);
    }

    public List<RecipeSuggestion> findAllRecipeSuggestions() {
        return recipeSuggestionRepo.findAll();
    }

    public RecipeSuggestion saveRecipeSuggestion(RecipeSuggestion recipeSuggestion) {
        return recipeSuggestionRepo.save(recipeSuggestion);
    }
}
