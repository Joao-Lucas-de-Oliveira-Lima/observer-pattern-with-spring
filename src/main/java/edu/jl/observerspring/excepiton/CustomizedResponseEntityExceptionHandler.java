package edu.jl.observerspring.excepiton;

import edu.jl.observerspring.dto.exception.GenericExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<GenericExceptionResponseDTO> handlerExceptions(
            WebRequest webRequest,
            Exception exception)
    {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GenericExceptionResponseDTO(
                        new Date(),
                        webRequest.getDescription(false),
                        exception.getMessage()));
    }
}
