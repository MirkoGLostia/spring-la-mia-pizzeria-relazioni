package org.exercise.java.springlamiapizzeriacrud.exceptions;

public class IngredientNameUniqueException extends RuntimeException{
    public IngredientNameUniqueException(String message) {
        super(message);
    }
}
