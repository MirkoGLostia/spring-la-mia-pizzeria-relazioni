package org.exercise.java.springlamiapizzeriacrud.exceptions;

public class OfferNotFoundException extends RuntimeException{
    public OfferNotFoundException(String message) {
        super(message);
    }
}
