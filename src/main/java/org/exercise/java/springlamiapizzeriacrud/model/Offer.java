package org.exercise.java.springlamiapizzeriacrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull
    private LocalDate startOffer;
    @NotNull
    private LocalDate endOffer;
    @NotBlank(message = "the offer needs a name")
    private String title;

    @NotNull
    @ManyToOne
    private Pizza pizza;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getStartOffer() {
        return startOffer;
    }

    public void setStartOffer(LocalDate startOffer) {
        this.startOffer = startOffer;
    }

    public LocalDate getEndOffer() {
        return endOffer;
    }

    public void setEndOffer(LocalDate endOffer) {
        this.endOffer = endOffer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Pizza getPizza() {
        return pizza;
    }

    public void setPizza(Pizza pizza) {
        this.pizza = pizza;
    }
}
