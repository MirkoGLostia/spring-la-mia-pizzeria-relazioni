package org.exercise.java.springlamiapizzeriacrud.service;

import org.exercise.java.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.exercise.java.springlamiapizzeriacrud.model.Offer;
import org.exercise.java.springlamiapizzeriacrud.model.Pizza;
import org.exercise.java.springlamiapizzeriacrud.repository.OfferRepository;
import org.exercise.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;
    @Autowired
    private OfferRepository offerRepository;

    public List<Pizza> getPizzaList(Optional<String> search) {
        if (search.isPresent()) {
            return pizzaRepository.findByNameContainingIgnoreCase(search.get());
        } else {
            return pizzaRepository.findAll();
        }
    }


    public Pizza getPizzaById(Integer id) throws PizzaNotFoundException {
        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isPresent()) {
            return result.get();
        } else {
            throw new PizzaNotFoundException("Pizza with id " + id + " not found");
        }
    }



    public Pizza editPizza(Pizza pizza) throws PizzaNotFoundException {
        Pizza pizzaToEdit = getPizzaById(pizza.getId());

        pizzaToEdit.setName(pizza.getName());
        pizzaToEdit.setDescription(pizza.getDescription());
        pizzaToEdit.setImage(pizza.getImage());
        pizzaToEdit.setPrice(pizza.getPrice());
        pizzaToEdit.setIngredients(pizza.getIngredients());

        return pizzaRepository.save(pizzaToEdit);
    }




    public void deletePizza(Integer id) {

        Pizza pizza = pizzaRepository.getReferenceById(id);

        for (Offer o : pizza.getOffers()) {
            offerRepository.delete(o);
        }

        pizzaRepository.deleteById(id);
    }

}
