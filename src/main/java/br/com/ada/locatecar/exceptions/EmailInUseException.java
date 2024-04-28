package br.com.ada.locatecar.exceptions;

public class EmailInUseException extends RuntimeException {

    public EmailInUseException() {
        super("Uh oh! The email you entered is already associated with an account. " +
                "Please try a different email address or consider logging in if this is your account.");
    }
}
