package com.cartwise.cartwise.controller;

import com.cartwise.cartwise.dto.RecipeSuggestionCreateDto;
import com.cartwise.cartwise.dto.RecipeSuggestionDto;
import com.cartwise.cartwise.mapper.RecipeSuggestionMapper;
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
    private final RecipeSuggestionMapper recipeSuggestionMapper;

    public RecipeSuggestionController(
            AiService aiService,
            GroceryItemService groceryItemService,
            RecipeSuggestionService recipeService,
            RecipeSuggestionMapper recipeSuggestionMapper
    ) {
        this.aiService = aiService;
        this.groceryItemService = groceryItemService;
        this.recipeService = recipeService;
        this.recipeSuggestionMapper = recipeSuggestionMapper;
    }

    @GetMapping("/generate")
    public ResponseEntity<List<RecipeSuggestionDto>> generateFromMyItems() {
        List<GroceryItem> myItems = groceryItemService.findAll();
        String raw = aiService.generateRecipesFromText(myItems);
        List<RecipeSuggestion> suggestions = aiService.parseRecipeJson(raw);
        List<RecipeSuggestionDto> dtos = suggestions.stream()
                .map(recipeSuggestionMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping
    public ResponseEntity<List<RecipeSuggestionDto>> listMyRecipes() {
        List<RecipeSuggestionDto> dtos = recipeService.findAllByUser().stream()
                .map(recipeSuggestionMapper::toDto)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeSuggestionDto> getMyRecipe(@PathVariable Long id) {
        return recipeService.findRecipeSuggestionById(id)
                .map(recipeSuggestionMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<RecipeSuggestionDto> saveMyRecipe(@RequestBody RecipeSuggestionCreateDto recipeDto) {
        RecipeSuggestion toSave = recipeSuggestionMapper.fromCreateDto(recipeDto);
        RecipeSuggestion saved = recipeService.saveRecipeSuggestion(toSave);
        RecipeSuggestionDto savedDto = recipeSuggestionMapper.toDto(saved);
        return ResponseEntity
                .created(URI.create("/api/recipes/" + saved.getRecipeId()))
                .body(savedDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMyRecipe(@PathVariable Long id) {
        recipeService.deleteForCurrentUser(id);
        return ResponseEntity.noContent().build();
    }
}
