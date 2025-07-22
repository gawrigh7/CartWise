package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private String ingredients;

    @ManyToMany(mappedBy = "recipe_suggestion")
    @JoinColumn(name = "grocery_item")
    private List<GroceryItem> groceryItem;
}
