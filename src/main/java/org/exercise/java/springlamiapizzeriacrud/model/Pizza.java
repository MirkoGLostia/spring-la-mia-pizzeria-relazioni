package org.exercise.java.springlamiapizzeriacrud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table (name = "pizzas")
public class Pizza {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "the pizza needs a name")
    @Size(max = 255, message = "not so long, under 255 you must")
    private String name;
    @NotBlank(message = "at least say what's in the pizza")
    @Lob
    private String description;
    @NotBlank(message = "the image needs a link")
    @Size(max = 255, message = "not so long, under 255 you must")
    private String image;
    @NotNull(message = "give a price or else it will be free")
    @DecimalMin(value = "0.01", inclusive = true)
    @Digits(integer=5, fraction = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "pizza")
    private List<Offer> offers = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }
}
