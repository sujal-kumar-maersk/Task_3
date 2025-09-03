package com.example.Task_3.errorhandler;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public String handleEntityNotFound(EntityNotFoundException ex, Model model) {
        ResponseEntity<String> res = new ResponseEntity<>("Error: " + ex.getMessage(), HttpStatus.NOT_FOUND);
        model.addAttribute("body", res.getBody());
        model.addAttribute("status", res.getStatusCode().value());
        return "error";
    }

    // Handles IllegalArgumentException
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgument(IllegalArgumentException ex,Model model) {
        ResponseEntity<String> res = new ResponseEntity<>("Invalid argument: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
        model.addAttribute("body", res.getBody());
        model.addAttribute("status", res.getStatusCode().value());
        return "error";
    }

    // 500 - Internal Server Error (Generic Fallback)
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("body", "An unexpected error occurred.");
        model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return "error";
    }
}
