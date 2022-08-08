package exception;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import talent.ql.user_age.util.TranslatorUtils;

import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author brume
 */
@ControllerAdvice
@AllArgsConstructor
@Slf4j
@Component
public class RestControllerExceptionHandler {

    private final ErrorAttributes errorAttributes;

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, WebRequest request) {
        List<String> messages = new ArrayList<>();
        exception.getFieldErrors().forEach(error -> {
            StringBuilder message = new StringBuilder();

            message.append(error.getObjectName());
            message.append(".");
            message.append(error.getField());
            message.append(": ");
            message.append(TranslatorUtils.toLocale(error.getDefaultMessage()));

            messages.add(message.toString());
        });
        Map<String, Object> errorAttributes = constructErrorResponse(HttpStatus.BAD_REQUEST, request, StringUtils.arrayToDelimitedString(messages.toArray(), ", "));

        return ResponseEntity.badRequest().body(errorAttributes);
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException exception, WebRequest request) {
        List<String> messages = new ArrayList<>();
        exception.getConstraintViolations().forEach(error -> messages.add(TranslatorUtils.toLocale(error.getMessage())));
        Map<String, Object> errorAttributes = constructErrorResponse(HttpStatus.BAD_REQUEST, request, StringUtils.arrayToDelimitedString(messages.toArray(), ", "));

        return ResponseEntity.badRequest().body(errorAttributes);
    }


    @ExceptionHandler({DateTimeParseException.class})
    public ResponseEntity<Object> handleDateTimeException(DateTimeParseException exception, WebRequest request) {

        log.info(exception.getMessage());
        Map<String, Object> errorAttributes = constructErrorResponse(HttpStatus.BAD_REQUEST, request, TranslatorUtils.toLocale("date.time.bad.format"));

        return ResponseEntity.badRequest().body(errorAttributes);
    }


    private Map<String, Object> constructErrorResponse(HttpStatus status, WebRequest request, String message) {
        Map<String, Object> errorAttributes = this.errorAttributes.getErrorAttributes(request, ErrorAttributeOptions.defaults());
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
        errorAttributes.put("message", message);
        errorAttributes.put("path", ((ServletWebRequest) request).getRequest().getRequestURI());

        return errorAttributes;
    }
}
