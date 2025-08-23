package com.cartwise.cartwise.service;

import com.cartwise.cartwise.model.GroceryItem;
import com.cartwise.cartwise.model.User;
import com.cartwise.cartwise.repository.GroceryItemRepo;
import com.cartwise.cartwise.repository.UserRepo;
import com.cartwise.cartwise.util.SecurityUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class GroceryItemService {

    private final GroceryItemRepo repo;
    private final UserRepo userRepo;

    public GroceryItemService(GroceryItemRepo repo, UserRepo userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    public Optional<GroceryItem> findByName(String name) {
        Long userId = SecurityUtil.getCurrentUserId();
        return repo.findByUserUserIdAndName(userId, name);
    }

    public List<GroceryItem> findAll() {
        Long userId = SecurityUtil.getCurrentUserId();
        return repo.findAllByUserUserId(userId);
    }

    public Optional<GroceryItem> findById(Long groceryId) {
        Long userId = SecurityUtil.getCurrentUserId();
        return repo.findByUserUserIdAndGroceryId(userId, groceryId);
    }

    @Transactional
    public GroceryItem save(GroceryItem item) {
        Long userId = SecurityUtil.getCurrentUserId();
        User ownerRef = userRepo.getReferenceById(userId);
        item.setUser(ownerRef);
        return repo.save(item);
    }

    @Transactional
    public void delete(Long groceryId) {
        Long userId = SecurityUtil.getCurrentUserId();
        long deleted = repo.deleteByUserUserIdAndGroceryId(userId, groceryId);
        if (deleted == 0) throw new RuntimeException("Item not found");
    }

    @Transactional
    public void deleteByIdInBatch(List<Long> ids) {
        Long userId = SecurityUtil.getCurrentUserId();
        if (ids != null && !ids.isEmpty()) {
            repo.deleteByUserIdAndIdIn(userId, ids);
        }
    }
}
