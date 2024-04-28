// ... (previous code)

    @ExceptionHandler(EmailInUseException.class)
    public ResponseEntity<MessageResponse> handleEmailInUseException(EmailInUseException ex) {
        // Use a more informative status code
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MessageResponse("That email address is already in use. Please try a different one."));
    }

    @ExceptionHandler(DocumentInUseException.class)
    public ResponseEntity<MessageResponse> handleDocumentInUseException(DocumentInUseException ex) {
        // Use a more informative status code
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new MessageResponse(ex.getMessage()));
    }
