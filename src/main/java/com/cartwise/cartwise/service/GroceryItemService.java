package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.repository.GroceryItemRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroceryItemService {

    private final GroceryItemRepo groceryItemRepo;

    public GroceryItemService(GroceryItemRepo groceryItemRepo) {
        this.groceryItemRepo = groceryItemRepo;
    }

    public Optional<GroceryItem> findByName(String name) {
        return groceryItemRepo.findByName(name);
    }

    public List<GroceryItem> findByType(String type) {
        return groceryItemRepo.findByType(type);
    }

    public List<GroceryItem> findAll() {
        return groceryItemRepo.findAll();
    }

    public Optional<GroceryItem> findById(Long groceryId) {
        return groceryItemRepo.findById(groceryId);
    }

    public GroceryItem save(GroceryItem groceryItem) {
        return groceryItemRepo.save(groceryItem);
    }

    @Transactional
    public void delete(GroceryItem groceryItem) {
        groceryItemRepo.delete(groceryItem);
    }

    @Transactional
    public void deleteById(Long id) {
        groceryItemRepo.deleteById(id);
    }
}
