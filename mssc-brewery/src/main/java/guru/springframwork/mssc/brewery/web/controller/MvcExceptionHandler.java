package guru.springframwork.mssc.brewery.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> validationErrorHandling(ConstraintViolationException e){
        List<String> error = new ArrayList<String>();

        e.getConstraintViolations().forEach(violations ->{
            error.add(">>>>>>>>>"+violations.getPropertyPath() +" : " + violations.getMessage());
        });

        return new  ResponseEntity<List<String>>(error, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler()
    public ResponseEntity<List<org.springframework.validation.ObjectError>> handleBindingException(BindException exception){
        return new ResponseEntity<>(exception.getAllErrors(),HttpStatus.BAD_REQUEST);
    }
}
