package com.cartwise.cartwise.repository;

import com.cartwise.cartwise.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroceryItemRepo extends JpaRepository<GroceryItem, Long> {

    Optional<GroceryItem> findByName(String name);
    void deleteAllByIdInBatch(Iterable<Long> groceryId);
}
