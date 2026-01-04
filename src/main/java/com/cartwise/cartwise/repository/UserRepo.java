package com.cartwise.cartwise.repository;

import com.cartwise.cartwise.model.RecipeSuggestion;
import com.cartwise.cartwise.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT u.favoriteRecipes FROM User u WHERE u.usersId = :userId")
    List<RecipeSuggestion> findFavoriteRecipesByUsersId(@Param("userId") Long userId);

    User findByUsersId(Long id);
    User findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    void deleteById(Long id);

}
