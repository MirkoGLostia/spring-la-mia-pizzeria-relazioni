package org.exercise.java.springlamiapizzeriacrud.controller;


import jakarta.validation.Valid;
import org.exercise.java.springlamiapizzeriacrud.exceptions.IngredientNameUniqueException;
import org.exercise.java.springlamiapizzeriacrud.model.Ingredient;
import org.exercise.java.springlamiapizzeriacrud.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/ingredient")
public class IngredientController {

    @Autowired
    IngredientService ingredientService;

    @GetMapping
    public String getIngredients(Model model) {
        model.addAttribute("area", "ingredient");

        model.addAttribute("ingredientList", ingredientService.getAll());
        model.addAttribute("ingredient", new Ingredient());

        return "ingredients/ingredientForm";
    }

    @PostMapping
    public String doSave(@Valid @ModelAttribute("ingredient") Ingredient formIngredient, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientList", ingredientService.getAll());
            return "ingredients/ingredientForm";
        }
        try {

            ingredientService.save(formIngredient);
            return "redirect:/ingredient";
        } catch (IngredientNameUniqueException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The ingredient " + e.getMessage() + " already exists");
        }
    }

}
