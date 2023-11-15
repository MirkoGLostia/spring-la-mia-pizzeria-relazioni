package org.exercise.java.springlamiapizzeriacrud.repository;

import org.exercise.java.springlamiapizzeriacrud.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Integer> {
}
