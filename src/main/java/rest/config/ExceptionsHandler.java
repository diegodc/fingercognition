package rest.config;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Define los diferentes handlers de exepciones para el servicio REST.
 */
@ControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler({MismatchedInputException.class, HttpMessageNotReadableException.class})
    public final ResponseEntity<ErrorMessage> handleInvalidRequest(RuntimeException e) {

        return ResponseEntity
                .badRequest()
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .body(new ErrorMessage("Invalid Request"));
    }

    public static class ErrorMessage {

        private final String message;

        ErrorMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

    }

}