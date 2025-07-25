package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.RecipeSuggestion;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiService {

    private final ChatClient chatClient;

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
        String input = items.stream()
                .map(GroceryItem::getName)
                .collect(Collectors.joining(", "));

        String prompt = """
    I have the following groceries: %s.
    Suggest 3 recipes I could cook using only these items.
    
    Return your response as a JSON array like this:
    [
      {
        "title": "Recipe Title",
        "description": "How to make it...",
        "ingredients": ["item1", "item2"]
      },
      ...
    ]
    """.formatted(input);

        System.out.println("Calling OpenAI with: " + prompt);
        return chat(prompt);
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
