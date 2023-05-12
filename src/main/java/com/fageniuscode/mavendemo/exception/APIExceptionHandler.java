package com.fageniuscode.mavendemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author Ibrahima
 *	gestion des exceptions
 */
@ControllerAdvice
public class APIExceptionHandler {

    /**
     * Gestion des exception de requête
     * @param e ->l'exeption levée à l'exécution de la requête
     * @return
     */
    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<APIException> handleRequestException(RequestException e) {
        APIException exception = new APIException(e.getMessage(),
                e.getStatus(), LocalDateTime.now());
        return new ResponseEntity<APIException>(exception, e.getStatus());
    }

    /**
     * Gestion des exception d'une réponse qui n'a pas était trouvé
     * @param e ->l'exeption levée à l'exécution de la requête
     * @return
     */
    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<APIException> handleEntityNotFoundException(EntityNotFoundException e) {
        APIException exception = new APIException(e.getMessage(),
                HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<APIException>(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<APIException> handleNumberFormatException(NumberFormatException e) {
        APIException exception = new APIException(HttpStatus.BAD_REQUEST.getReasonPhrase(),
                HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<APIException>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<APIException> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        APIException exception = new APIException("The input provided is invalid",
                HttpStatus.BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<APIException>(exception, HttpStatus.BAD_REQUEST);
    }

}
