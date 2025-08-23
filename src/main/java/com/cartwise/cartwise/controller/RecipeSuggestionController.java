package com.cartwise.cartwise.controller;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.service.AiService;
import com.cartwise.cartwise.service.GroceryItemService;
import com.cartwise.cartwise.service.RecipeSuggestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/recipes")
public class RecipeSuggestionController {

    private final RecipeSuggestionService recipeService;
    private final AiService aiService;
    private final GroceryItemService groceryItemService;

    public RecipeSuggestionController(
            AiService aiService,
            GroceryItemService groceryItemService,
            RecipeSuggestionService recipeService
    ) {
        this.aiService = aiService;
        this.groceryItemService = groceryItemService;
        this.recipeService = recipeService;
    }

    @GetMapping("/generate")
    public ResponseEntity<List<RecipeSuggestion>> generateFromMyItems() {
        List<GroceryItem> myItems = groceryItemService.findAll();
        String raw = aiService.generateRecipesFromText(myItems);
        List<RecipeSuggestion> suggestions = aiService.parseRecipeJson(raw);
        return ResponseEntity.ok(suggestions);
    }

    @GetMapping
    public ResponseEntity<List<RecipeSuggestion>> listMyRecipes() {
        return ResponseEntity.ok(recipeService.findAllByUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeSuggestion> getMyRecipe(@PathVariable Long id) {
        return recipeService.findRecipeSuggestionById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecipeSuggestion> saveMyRecipe(@RequestBody RecipeSuggestion recipe) {
        RecipeSuggestion saved = recipeService.saveRecipeSuggestion(recipe);
        return ResponseEntity.created(URI.create("/api/recipes/" + saved.getRecipeId()))
                .body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyRecipe(@PathVariable Long id) {
        recipeService.deleteForCurrentUser(id);
        return ResponseEntity.noContent().build();
    }
}
