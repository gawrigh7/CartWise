package com.cartwise.cartwise.repository;

import com.cartwise.cartwise.model.GroceryItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroceryItemRepo extends JpaRepository<GroceryItem, Long> {

    Optional<GroceryItem> findByUserUsersIdAndName(Long userId, String name);
    Optional<GroceryItem> findByUserUsersIdAndGroceryId(Long userId, Long groceryId);
    List<GroceryItem> findAllByUserUsersId(Long userId);
    void deleteByUserUsersIdAndGroceryId(Long userId, Long groceryId);
    @Modifying
    @Query("delete from GroceryItem g where g.user.usersId = :userId and g.groceryId in :ids")
    int deleteByUserUsersIdAndIdIn(@Param("userId") Long userId, @Param("ids") List<Long> ids);



}
