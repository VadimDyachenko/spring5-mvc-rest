package ua.vadym.spring5mvcrest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.vadym.spring5mvcrest.exceptions.ErrorDescription;
import ua.vadym.spring5mvcrest.exceptions.ApplicationException;

@ControllerAdvice
public class RestResponseEntityExceptionHandler {

    @ExceptionHandler({ApplicationException.class})
    @ResponseBody
    public ResponseEntity<ErrorDescription> handleNotFoundException(ApplicationException ex) {
        ErrorDescription description = ex.description();
        return new ResponseEntity<>(description, HttpStatus.valueOf(description.statusCode()));
    }
}
