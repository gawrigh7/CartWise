package com.cartwise.cartwise.controller;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.service.GroceryItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/grocery_item")
public class GroceryItemController {

    private final GroceryItemService groceryItemService;

    public GroceryItemController(GroceryItemService groceryItemService) {
        this.groceryItemService = groceryItemService;
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<GroceryItem> getGroceryItemByName(@PathVariable String name) {
        Optional<GroceryItem> grocery = groceryItemService.findByName(name);

        return ResponseEntity.ok(grocery.orElse(new GroceryItem()));
    }

    @GetMapping ("/type/{type}")
    public ResponseEntity<GroceryItem> getGroceryItemByType(@PathVariable String type) {
        List<GroceryItem> groceryList = groceryItemService.findByType(type);
        return ResponseEntity.ok(groceryList.stream().findFirst().get());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<GroceryItem> getGroceryItemById(@PathVariable Long id) {
        Optional<GroceryItem> grocery = groceryItemService.findById(id);
        return ResponseEntity.ok(grocery.orElse(new GroceryItem()));
    }

    @GetMapping
    public ResponseEntity<List<GroceryItem>> getAllGroceryItems() {
        return ResponseEntity.ok(groceryItemService.findAll());
    }

    @PostMapping
    public ResponseEntity<GroceryItem> addGroceryItem(@RequestBody GroceryItem groceryItem) {
        groceryItemService.save(groceryItem);
        return ResponseEntity.ok(groceryItem);
    }

    @PutMapping
    public ResponseEntity<GroceryItem> updateGroceryItem(@RequestBody GroceryItem groceryItem) {
        groceryItemService.save(groceryItem);
        return ResponseEntity.ok(groceryItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroceryItem(@PathVariable Long id) {
        Optional<GroceryItem> grocery = groceryItemService.findById(id);
        if (grocery.isPresent()) {
            groceryItemService.delete(grocery.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
