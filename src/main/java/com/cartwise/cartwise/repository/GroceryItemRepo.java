package com.cartwise.cartwise.repository;

import com.cartwise.cartwise.model.GroceryItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroceryItemRepo extends JpaRepository<GroceryItem, Long> {

    Optional<GroceryItem> findByUserUserIdAndName(Long userId, String name);

    Optional<GroceryItem> findByUserUserIdAndGroceryId(Long userId, Long groceryId);

    List<GroceryItem> findAllByUserUserId(Long userId);

    long deleteByUserUserIdAndGroceryId(Long userId, Long groceryId);

    @Modifying
    @Query("delete from GroceryItem g where g.user.userId = :userId and g.groceryId in :ids")
    int deleteByUserIdAndIdIn(@Param("userId") Long userId, @Param("ids") List<Long> ids);
}
