package br.com.catalisa.ZuPlaceApi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDate;

@ControllerAdvice
public class ExceptionHandlerController {
    @ExceptionHandler({CepNullException.class, CepFormatException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(Exception ex) {
        ExceptionResponse errorDetails = new ExceptionResponse(LocalDate.now(),ex.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ResourseNotFoundException.class)
    public ResponseEntity<Object> HandleObjectNotFoundException(Exception exception){
        ExceptionResponse errorDetails = new ExceptionResponse(LocalDate.now(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }
}
