package pl.coderslab.planespotter.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class AllExceptionsHandler {

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleNotFound(ResourceNotFoundException exception){

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", exception.getMessage()));

    }

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleValidation(MethodArgumentNotValidException exception){

        Map<String, List<String>> errors = new HashMap<>();

        exception.getBindingResult().getFieldErrors()
                .forEach(error -> {
                    String field = error.getField();
                    String message = error.getDefaultMessage();

                    errors.computeIfAbsent(field, key -> new ArrayList<>()).add(message);
                });

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler
    public ResponseEntity<Map<String, List<String>>> handleDuplicate(DuplicateResourceException exception){


        return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.getErrors());

    }

    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleAuthentication(AuthenticationException exception){

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("error", "Invalid username or password"));
    }
}
