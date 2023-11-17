package org.exercise.java.springlamiapizzeriacrud.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.exercise.java.springlamiapizzeriacrud.model.Ingredient;

import java.util.List;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    List<Ingredient> findByOrderByName();

    boolean existsByName(String name);


}
