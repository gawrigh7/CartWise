package model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NutritionValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nutritionId;

    private int calories;
    private int proteins;
    private int fats;
    private int carbohydrates;

    @OneToOne
    private GroceryItem groceryItem;

}
