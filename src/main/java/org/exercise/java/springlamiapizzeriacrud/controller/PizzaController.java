package org.exercise.java.springlamiapizzeriacrud.controller;


import jakarta.validation.Valid;
import org.exercise.java.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.exercise.java.springlamiapizzeriacrud.model.Pizza;
import org.exercise.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.exercise.java.springlamiapizzeriacrud.service.IngredientService;
import org.exercise.java.springlamiapizzeriacrud.service.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.Binding;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizza")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaService pizzaService;
    @Autowired
    private IngredientService ingredientService;


    @GetMapping
    public String pizzaMenu(@RequestParam Optional<String> search, Model model) {
        model.addAttribute("area", "pizza-list");
        model.addAttribute("pizzaList", pizzaService.getPizzaList(search));
        return "pizzas/list";
    }


    @GetMapping("/detail/{id}")
    public String pizzaDetail(@PathVariable Integer id, Model model) {
        model.addAttribute("area", "detail-pizza");

        try {
            Pizza pizza = pizzaService.getPizzaById(id);
            model.addAttribute("pizza", pizza);
            return "pizzas/detail";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }


    @GetMapping("/create")
    public String createPizza(Model model) {
        model.addAttribute("area", "pizza-create");
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredientList", ingredientService.getAll());
        return "pizzas/createEdit";
    }

    @PostMapping("/create")
    public String doCreatePizza(Model model, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        model.addAttribute("area", "pizza-create");

        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientList", ingredientService.getAll());
            return "pizzas/createEdit";
        }


        Pizza savedPizza = pizzaRepository.save(formPizza);
        return "redirect:/pizza/detail/" + savedPizza.getId();
    }



    @GetMapping("/edit/{id}")
    public String editPizza(@PathVariable Integer id, Model model) {
        try {
            model.addAttribute("pizza", pizzaService.getPizzaById(id));
            model.addAttribute("ingredientList", ingredientService.getAll());
            return "pizzas/createEdit";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }



    @PostMapping("/edit/{id}")
    public String doEditPizza(Model model, @PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredientList", ingredientService.getAll());
            return "pizzas/createEdit";
        }
        try {
            Pizza editPizza = pizzaService.editPizza(formPizza);
            return "redirect:/pizza/detail/" + editPizza.getId();
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }




    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Pizza pizzaToDelete = pizzaService.getPizzaById(id);
            pizzaService.deletePizza(id);
            redirectAttributes.addFlashAttribute("message",
                    "Pizza " + pizzaToDelete.getName() + " deleted!");
            return "redirect:/pizza";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }



}
