package com.cartwise.cartwise.repository;

import com.cartwise.cartwise.model.RecipeSuggestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RecipeSuggestionRepo extends JpaRepository<RecipeSuggestion, Long> {

    Optional<RecipeSuggestion> findByTitle(String title);



}
