package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.User;
import com.cartwise.cartwise.repository.GroceryItemRepo;
import com.cartwise.cartwise.repository.UserRepo;
import com.cartwise.cartwise.util.SecurityUtil;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroceryItemService {

    private final GroceryItemRepo groceryItemRepo;
    private final UserRepo userRepo;

    public GroceryItemService(GroceryItemRepo groceryItemRepo, UserRepo userRepo) {
        this.groceryItemRepo = groceryItemRepo;
        this.userRepo = userRepo;
    }

    public Optional<GroceryItem> findByName(String name) {
        Long userId = SecurityUtil.getCurrentUserId();
        return groceryItemRepo.findByUserUsersIdAndName(userId, name);
    }


    public List<GroceryItem> findAll() {
        Long userId = SecurityUtil.getCurrentUserId();
        return groceryItemRepo.findAllByUserUsersId(userId);
    }

    public Optional<GroceryItem> findById(Long groceryId) {
        Long userId = SecurityUtil.getCurrentUserId();
        return groceryItemRepo.findByUserUsersIdAndGroceryId(userId, groceryId);
    }

    @Transactional
    public GroceryItem save(GroceryItem groceryItem) {
        Long userId = SecurityUtil.getCurrentUserId();
        User ownerRef = userRepo.getReferenceById(userId);
        groceryItem.setUser(ownerRef);
        return groceryItemRepo.save(groceryItem);
    }

    @Transactional
    public void delete(Long groceryId) {
        Long userId = SecurityUtil.getCurrentUserId();
        groceryItemRepo.deleteByUserUsersIdAndGroceryId(userId, groceryId);
    }

    @Transactional
    public void deleteByIdInBatch(List<Long> groceryItems) {
        Long userId = SecurityUtil.getCurrentUserId();
        groceryItemRepo.deleteByUserUsersIdAndIdIn(userId, groceryItems);
    }

}
