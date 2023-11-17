package org.exercise.java.springlamiapizzeriacrud.exceptions;

public class IngredientNotFoundException extends RuntimeException{
    public IngredientNotFoundException(String message) {
        super(message);
    }
}
