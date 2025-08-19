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
    private boolean available;


    @ManyToOne
    @JsonIgnore
    private Users users;

    @ManyToMany
    @JoinTable(
            name = "grocery_item_recipe_suggestion",
            joinColumns = @JoinColumn(name = "grocery_item_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_suggestion_id")
    )
    @JsonIgnore
    private List<RecipeSuggestion> recipeSuggestion;


}
