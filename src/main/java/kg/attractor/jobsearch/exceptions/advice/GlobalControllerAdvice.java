package kg.attractor.jobsearch.exceptions.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.InputElementExceptionBody;
import kg.attractor.jobsearch.exceptions.body.ValidationExceptionBody;
import kg.attractor.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.format.DateTimeParseException;
import java.util.Map;

@ControllerAdvice
@RequiredArgsConstructor
@Valid
public class GlobalControllerAdvice {
    private final ErrorService errorService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionBody handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request
    ) {
        return errorService.handleValidationException(ex, request);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNoSuchElementException(
            Model model, EntityNotFoundException ex, HttpServletRequest request
    ) {
        model.addAttribute("status", HttpStatus.NOT_FOUND.value());
        model.addAttribute("reason", HttpStatus.NOT_FOUND.getReasonPhrase());
        model.addAttribute("message", ex.getMessage());
        model.addAttribute("details", request);
        return "errors/error";
    }

    @ExceptionHandler(CustomIllegalArgException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InputElementExceptionBody handleCustomIllegalArgException(
            CustomIllegalArgException ex, HttpServletRequest request
    ) {
        return errorService.handleInvalidArgumentException(ex, request);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleHandlerMethodValidationException(
            HandlerMethodValidationException ex, HttpServletRequest request
    ) {
        return errorService.handleMethodValidationException(ex, request);
    }

    @ExceptionHandler(DateTimeParseException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handleDateTimeParseException(DateTimeParseException ex) {
        return errorService.handleDateTimeParserException(ex);
    }
}
