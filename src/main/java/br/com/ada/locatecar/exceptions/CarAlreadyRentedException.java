package br.com.ada.locatecar.exceptions;

public class CarAlreadyRentedException extends RuntimeException {
    public CarAlreadyRentedException() {
        super("This car is already rented");
    }
}
