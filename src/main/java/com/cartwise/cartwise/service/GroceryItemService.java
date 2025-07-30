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
    private final AiService aiService;

    public GroceryItemService(GroceryItemRepo groceryItemRepo, AiService aiService) {
        this.groceryItemRepo = groceryItemRepo;
        this.aiService = aiService;
    }

    public Optional<GroceryItem> findByName(String name) {
        return groceryItemRepo.findByName(name);
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
    public void deleteByIdInBatch(List<GroceryItem> groceryItems) {
        groceryItemRepo.deleteAllInBatch(groceryItems);
    }

    @Transactional
    public void deleteById(Long id) {
        groceryItemRepo.deleteById(id);
    }
}
