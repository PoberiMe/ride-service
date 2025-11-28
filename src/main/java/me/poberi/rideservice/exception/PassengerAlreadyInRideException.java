package me.poberi.rideservice.exception;

public class PassengerAlreadyInRideException extends RuntimeException {
    public PassengerAlreadyInRideException(String message) {
        super(message);
    }
}
