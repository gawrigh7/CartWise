package com.cartwise.cartwise.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeSuggestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recipeId;

    private String title;
    private String description;
    private boolean favorite;


    @ManyToMany
    @JoinTable(
            name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "grocery_item_id")
    )
    private List<GroceryItem> ingredients;

    @ManyToMany(mappedBy = "recipeSuggestion")
    private List<GroceryItem> groceryItem;

    @ManyToMany(mappedBy = "favoriteRecipes")
    private Set<Users> usersWhoFavorited = new HashSet<>();

}
