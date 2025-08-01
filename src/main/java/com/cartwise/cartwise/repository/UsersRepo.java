package com.cartwise.cartwise.repository;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

    @Query("SELECT u.favoriteRecipes FROM Users u WHERE u.usersId = :userId")
    List<RecipeSuggestion> findFavoriteRecipesByUsersId(@Param("userId") Long userId);

    Users findByUsername(String username);

}
