package org.exercise.java.springlamiapizzeriacrud.service;

import org.exercise.java.springlamiapizzeriacrud.exceptions.IngredientNameUniqueException;
import org.exercise.java.springlamiapizzeriacrud.exceptions.IngredientNotFoundException;
import org.exercise.java.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.exercise.java.springlamiapizzeriacrud.model.Ingredient;
import org.exercise.java.springlamiapizzeriacrud.model.Pizza;
import org.exercise.java.springlamiapizzeriacrud.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    IngredientRepository ingredientRepository;

    public List<Ingredient> getAll() {
        return ingredientRepository.findByOrderByName();
    }

    public Ingredient save(Ingredient ingredient) throws IngredientNameUniqueException {
        if (ingredientRepository.existsByName(ingredient.getName())) {
            throw new IngredientNameUniqueException(ingredient.getName());
        }

        ingredient.setName(ingredient.getName().toLowerCase());

        return ingredientRepository.save(ingredient);
    }

    public Ingredient getIngredientById(Integer id) throws IngredientNotFoundException {
        Optional<Ingredient> result = ingredientRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new IngredientNotFoundException("Ingredient with id " + id + " not found");
        }
    }

    public void deleteIngredient(Integer id) {
        ingredientRepository.deleteById(id);
    }



}
