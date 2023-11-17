package org.exercise.java.springlamiapizzeriacrud.controller;


import jakarta.validation.Valid;
import org.exercise.java.springlamiapizzeriacrud.exceptions.OfferNotFoundException;
import org.exercise.java.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.exercise.java.springlamiapizzeriacrud.model.Ingredient;
import org.exercise.java.springlamiapizzeriacrud.model.Offer;
import org.exercise.java.springlamiapizzeriacrud.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/offer")
public class OfferController {

    @Autowired
    OfferService offerService;

    @GetMapping("/create")
    public String create(@RequestParam Integer pizzaId, Model model) {
        model.addAttribute("area", "offer-creation");

        try {
            model.addAttribute("offer", offerService.createNewOffer(pizzaId));
            return "offers/offerForm";
        } catch (PizzaNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/create")
    public String doCreate(@Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "offers/offerForm";
        }
        Offer savedOffer = offerService.saveOffer(formOffer);

        return "redirect:/pizza/detail/" + formOffer.getPizza().getId();
    }



    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        try {
            Offer offer = offerService.getOffer(id);
            model.addAttribute("offer", offer);
            return "offers/offerForm";
        } catch (OfferNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id, @Valid @ModelAttribute("offer") Offer formOffer, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "borrowings/form";
        }

        Offer savedOffer = offerService.saveOffer(formOffer);

        return "redirect:/pizza/detail/" + formOffer.getPizza().getId();
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
            Offer offerToDelete = offerService.getOffer(id);
            offerService.deleteOffer(id);
            redirectAttributes.addFlashAttribute("message",
                    "Offer " + offerToDelete.getTitle() + " deleted!");
            return "redirect:/pizza/detail/" + offerToDelete.getPizza().getId();
    }





}
