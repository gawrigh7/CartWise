package com.cartwise.cartwise.controller;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.service.AiService;
import com.cartwise.cartwise.service.GroceryItemService;
import com.cartwise.cartwise.service.RecipeSuggestionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/recipe")
public class RecipeSuggestionController {

    private final RecipeSuggestionService recipeSuggestionService;
    private final AiService aiService;
    private final GroceryItemService groceryItemService;

    public RecipeSuggestionController(AiService aiService, GroceryItemService groceryItemService, RecipeSuggestionService recipeSuggestionService) {
        this.aiService = aiService;
        this.groceryItemService = groceryItemService;
        this.recipeSuggestionService = recipeSuggestionService;
    }

    @GetMapping("/generate")
    public ResponseEntity<List<RecipeSuggestion>> recipeFromItems() {
        // List<GroceryItem> groceryItems = groceryItemService.findAll();
        GroceryItem cheese = new GroceryItem();
        cheese.setName("Cheese");
        GroceryItem bread = new GroceryItem();
        bread.setName("Bread");
        List<GroceryItem> groceryItems = Arrays.asList(cheese, bread);
        String recipeGen = aiService.generateRecipesFromText(groceryItems);
        System.out.println(recipeGen);
        List<RecipeSuggestion> recipeList = aiService.parseRecipeJson(recipeGen);
        return ResponseEntity.ok(recipeList);
    }
    @GetMapping("/favorites")
    public ResponseEntity<List<RecipeSuggestion>> getFavoritesByUser(@RequestParam Long userId) {
        List<RecipeSuggestion> recipes = recipeSuggestionService.findAllByUser(userId);
        return ResponseEntity.ok(recipes);
    }

    @PostMapping("/favorite")
    public ResponseEntity<RecipeSuggestion> saveRecipeSuggestion(@RequestBody  RecipeSuggestion recipeSuggestion, @RequestParam Long userId) {
        RecipeSuggestion rs = recipeSuggestionService.saveRecipeSuggestion(recipeSuggestion, userId);
        return ResponseEntity.status(201).body(rs);
    }


    @DeleteMapping("/unfavorite")
    public ResponseEntity<Void> unfavoriteRecipe(@RequestParam Long recipeId, @RequestParam Long userId) {
        recipeSuggestionService.unfavoriteRecipe(recipeId, userId);
        return ResponseEntity.noContent().build();
    }

}
