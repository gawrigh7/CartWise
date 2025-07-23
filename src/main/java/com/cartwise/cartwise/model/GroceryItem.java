package com.cartwise.cartwise.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groceryId;

    private String name;
    private String type;
    private boolean available;

    @OneToOne
    @JoinColumn(name = "nutrition_value", nullable = false)
    private NutritionValue nutritionValue;

    @ManyToOne
    @JsonIgnore
    private User user;

    @ManyToMany
    @JoinTable(
            name = "grocery_item_recipe_suggestion",
            joinColumns = @JoinColumn(name = "grocery_item_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_suggestion_id")
    )
    @JsonIgnore
    private List<RecipeSuggestion> recipeSuggestion;


}
