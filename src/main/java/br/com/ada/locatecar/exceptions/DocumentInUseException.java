package br.com.ada.locatecar.exceptions;

public class DocumentInUseException extends RuntimeException {

    public DocumentInUseException(String documentType) {
        super("Hold on! It seems the " + documentType + " you provided is already in use. " +
                "Please double-check or contact support if you believe this is an error.");
    }
}
