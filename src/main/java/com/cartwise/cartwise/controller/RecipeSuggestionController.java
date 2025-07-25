package com.cartwise.cartwise.controller;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.service.AiService;
import com.cartwise.cartwise.service.GroceryItemService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/recipe")
public class RecipeSuggestionController {

    private final AiService aiService;
    private final GroceryItemService groceryItemService;

    public RecipeSuggestionController(AiService aiService, GroceryItemService groceryItemService) {
        this.aiService = aiService;
        this.groceryItemService = groceryItemService;
    }

    @GetMapping("/generate")
    public ResponseEntity<List<RecipeSuggestion>> recipeFromItems() {
        List<GroceryItem> groceryItems = groceryItemService.findAll();
        String recipeGen = aiService.generateRecipesFromText(groceryItems);
        List<RecipeSuggestion> recipeList = aiService.parseRecipeJson(recipeGen);
        return ResponseEntity.ok(recipeList);
    }
}
