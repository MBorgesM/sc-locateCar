package br.com.ada.locatecar.exceptions;

import br.com.ada.locatecar.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<?> handleEmailInUseException(EmailInUseException ex) {
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

    @ExceptionHandler(DocumentInUseException.class)
    public ResponseEntity<?> handleDocumentInUseException(DocumentInUseException ex) {
        return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
    }

}
