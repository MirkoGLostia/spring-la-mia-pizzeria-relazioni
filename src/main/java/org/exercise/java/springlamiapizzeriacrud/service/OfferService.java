package org.exercise.java.springlamiapizzeriacrud.service;

import org.exercise.java.springlamiapizzeriacrud.exceptions.OfferNotFoundException;
import org.exercise.java.springlamiapizzeriacrud.exceptions.PizzaNotFoundException;
import org.exercise.java.springlamiapizzeriacrud.model.Pizza;
import org.exercise.java.springlamiapizzeriacrud.model.Offer;
import org.exercise.java.springlamiapizzeriacrud.repository.OfferRepository;
import org.exercise.java.springlamiapizzeriacrud.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class OfferService {

    @Autowired
    PizzaRepository pizzaRepository;
    @Autowired
    OfferRepository offerRepository;



    public Offer createNewOffer(Integer pizzaId) throws PizzaNotFoundException {
        Pizza pizza = pizzaRepository.findById(pizzaId).orElseThrow(() -> new PizzaNotFoundException("Pizza with id " + pizzaId + " not found"));
        Offer offer = new Offer();
        offer.setStartOffer(LocalDate.now());
        offer.setEndOffer(LocalDate.now().plusMonths(1));
        offer.setPizza(pizza);

        return offer;
    }


    public Offer saveOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer getOffer(Integer id) throws OfferNotFoundException {
        return offerRepository.findById(id).orElseThrow(() -> new OfferNotFoundException("Offer with id " + id + " not found"));
    }

    public void deleteOffer(Integer id) {
        offerRepository.deleteById(id);
    }


}
