package flashcards.exceptions;

import flashcards.exceptions.customexceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        ErrorResponse error = ErrorResponse.builder()
                .httpStatus(HttpStatus.UNAUTHORIZED)
                .message(ex.getMessage())
                .createdAt(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(Exception ex){

        ErrorResponse error = ErrorResponse.builder()
                .httpStatus(HttpStatus.FORBIDDEN)
                .message(ex.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error,error.getHttpStatus());
    }

    @ExceptionHandler(CollectionNotFoundException.class)
    public ResponseEntity<Object> handleCollectionNotFoundException(Exception ex){

        ErrorResponse error = ErrorResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error,error.getHttpStatus());
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<Object> handleCardNotFoundException(Exception ex){

        ErrorResponse error = ErrorResponse.builder()
                .httpStatus(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error,error.getHttpStatus());
    }


    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(Exception ex){

        ErrorResponse error = ErrorResponse.builder()
                .httpStatus(HttpStatus.CONFLICT)
                .message(ex.getMessage())
                .createdAt(LocalDateTime.now())
                .build();

        return new ResponseEntity<>(error,error.getHttpStatus());
    }

}
