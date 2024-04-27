package br.com.ada.locatecar.exceptions;

public class DocumentInUseException extends RuntimeException {
    public DocumentInUseException(String message) {
        super(message);
    }
}
