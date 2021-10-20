package guru.springframwork.msscbeerservice.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.util.*;

@ControllerAdvice
public class MvcExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validateErrorHandling(ConstraintViolationException exception){
        ArrayList list = new ArrayList();
       exception.getConstraintViolations().forEach(error  -> {
                list.add(">>>>>>>>>>>>" + error.toString());
        });
        return new ResponseEntity<List>(HttpStatus.BAD_REQUEST);
    }
}
