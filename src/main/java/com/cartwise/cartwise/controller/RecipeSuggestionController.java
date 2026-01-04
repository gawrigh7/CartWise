package com.cartwise.cartwise.controller;

import com.cartwise.cartwise.dto.RecipeSuggestionDto;
import com.cartwise.cartwise.mapper.RecipeSuggestionMapper;
import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.service.AiService;
import com.cartwise.cartwise.service.GroceryItemService;
import com.cartwise.cartwise.service.RecipeSuggestionService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private final RecipeSuggestionMapper recipeSuggestionMapper;

    public RecipeSuggestionController(
            AiService aiService,
            GroceryItemService groceryItemService,
            RecipeSuggestionService recipeService,
            RecipeSuggestionMapper recipeSuggestionMapper) {
        this.aiService = aiService;
        this.groceryItemService = groceryItemService;
        this.recipeService = recipeService;
        this.recipeSuggestionMapper = recipeSuggestionMapper;
    }

    @PostMapping("/generate")
    public ResponseEntity<List<RecipeSuggestionDto>> generateFromMyItems() {
        List<GroceryItem> myItems = groceryItemService.findAll();
        String raw = aiService.generateRecipesFromText(myItems);
        List<RecipeSuggestion> suggestions = aiService.parseRecipeJson(raw);
        List<RecipeSuggestionDto> suggestionDto = suggestions.stream()
                .map(recipeSuggestionMapper::toRecipeDto).toList();
        return ResponseEntity.ok(suggestionDto);
    }

    @GetMapping
    public ResponseEntity<List<RecipeSuggestionDto>> listMyRecipes() {
        List<RecipeSuggestionDto> recipeDto = recipeService.findAllByUser()
                .stream().map(recipeSuggestionMapper::toRecipeDto).toList();
        return ResponseEntity.ok(recipeDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeSuggestionDto> getMyRecipe(@PathVariable Long id) {
        return recipeService.findRecipeSuggestionById(id).map(recipeSuggestionMapper::toRecipeDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecipeSuggestionDto> saveMyRecipe(@RequestBody RecipeSuggestionDto recipe) {
        RecipeSuggestion save = recipeService.saveRecipeSuggestion(recipeSuggestionMapper.fromRecipeCreateDto(recipe));
        RecipeSuggestionDto savedDto = recipeSuggestionMapper.toRecipeDto(save);
        return ResponseEntity
                .created(URI.create("/api/recipes/" + savedDto.getRecipeId()))
                .body(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyRecipe(@PathVariable Long id) {
        recipeService.deleteForCurrentUser(id);
        return ResponseEntity.noContent().build();
    }

}
