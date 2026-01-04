package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.RecipeSuggestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class AiService {

    private final ChatClient chatClient;

    private final Cache<String, String> recipeJsonCache = Caffeine.newBuilder()
            .maximumSize(10_000)
            .expireAfterWrite(Duration.ofHours(24))
            .build();

    public AiService(ChatClient.Builder builder) {
        chatClient = builder.build();
    }

    public String chat(String message) {
        return chatClient
                .prompt(message)
                .call()
                .content();
    }
    public String generateRecipesFromText(List<GroceryItem> items) {
        String requestKey = buildRequestKey(items);

        String cached = recipeJsonCache.getIfPresent(requestKey);
        if (cached != null) {
            return cached;
        }

        String input = items.stream()
                .map(GroceryItem::getName)
                .collect(Collectors.joining(", "));

        if (input.isEmpty()) {
            throw new RuntimeException("Empty recipe list");
        }

            String systemMessage = """
    You are a recipe assistant that responds ONLY in valid JSON format.
    Never include any text outside of the JSON response.
    Always return a JSON array of recipe objects.
    """;

        String userPrompt = """
    I have the following groceries: %s.
    Suggest 3 recipes I could cook using only these items.
    
    Return as JSON array:
    [
      {
        "title": "Recipe Title",
        "description": "How to make it..."
      }
    ]
    """.formatted(input);

        String json = chatClient
                .prompt()
                .system(systemMessage)
                .user(userPrompt)
                .call()
                .content();

        recipeJsonCache.put(requestKey, json);

        return json;
    }

    private String buildRequestKey(List<GroceryItem> items) {
        if (items == null) return "items:null";

        List<String> normalized = items.stream()
                .map(GroceryItem::getName)
                .filter(Objects::nonNull)
                .map(String::trim)
                .map(String::toLowerCase)
                .filter(s -> !s.isBlank())
                .sorted()
                .toList();

        if (normalized.isEmpty()) return "items:null";

        return "items:" + normalized;
    }

    public List<RecipeSuggestion> parseRecipeJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Arrays.asList(mapper.readValue(json, RecipeSuggestion[].class));
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }
    }


}
