package org.exercise.java.springlamiapizzeriacrud.controller;


import jakarta.validation.Valid;
import org.exercise.java.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.exercise.java.springlamiapizzeriacrud.model.Pizza;
import org.exercise.java.springlamiapizzeriacrud.repository.PizzaRepository;
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
@RequestMapping("/")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private PizzaService pizzaService;


    @GetMapping ("pizza-list")
    public String pizzaMenu(@RequestParam Optional<String> search, Model model) {
        model.addAttribute("area", "pizza-list");
        model.addAttribute("pizzaList", pizzaService.getPizzaList(search));
        return "pizzas/list";
    }


    @GetMapping("pizza-list/detail/{id}")
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


    @GetMapping("pizza/create")
    public String createPizza(Model model) {
        model.addAttribute("area", "pizza-create");
        model.addAttribute("pizza", new Pizza());
        return "pizzas/createEdit";
    }

    @PostMapping("pizza/create")
    public String doCreatePizza(Model model, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        model.addAttribute("area", "pizza-create");

        if (bindingResult.hasErrors()) {
            return "pizzas/createEdit";
        }


        Pizza savedPizza = pizzaRepository.save(formPizza);
        return "redirect:/pizza-list/detail/" + savedPizza.getId();
    }



    @GetMapping("pizza/edit/{id}")
    public String editPizza(@PathVariable Integer id, Model model) {
        try {
            model.addAttribute("pizza", pizzaService.getPizzaById(id));
            return "pizzas/createEdit";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }



    @PostMapping("pizza/edit/{id}")
    public String doEditPizza(@PathVariable Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "pizzas/createEdit";
        }
        try {
            Pizza editPizza = pizzaService.editPizza(formPizza);
            return "redirect:/pizza-list/detail/" + editPizza.getId();
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }




    @PostMapping("pizza/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Pizza pizzaToDelete = pizzaService.getPizzaById(id);
            pizzaService.deletePizza(id);
            redirectAttributes.addFlashAttribute("message",
                    "Pizza " + pizzaToDelete.getName() + " deleted!");
            return "redirect:/pizza-list";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }



}
