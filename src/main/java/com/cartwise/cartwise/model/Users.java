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
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long usersId;

    private String username;
    private String password;
    private String email;

    @OneToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<GroceryItem> groceryItem;

    @ManyToMany
    @JoinTable (
        name = "users_favorites",
                joinColumns = @JoinColumn(name = "users_id"),
        inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    public Set<RecipeSuggestion> favoriteRecipes = new HashSet<>();
}
