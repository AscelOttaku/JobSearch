package kg.attractor.jobsearch.exceptions.advice;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.InputElementExceptionBody;
import kg.attractor.jobsearch.exceptions.body.ValidationErrorBody;
import kg.attractor.jobsearch.exceptions.body.ValidationExceptionBody;
import kg.attractor.jobsearch.service.ErrorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalControllerAdvice {
    private final ErrorService errorService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ValidationExceptionBody handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request
    ) {
        List<ValidationErrorBody> errorBodies = errorService.handleValidationException(ex);
        return ValidationExceptionBody.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(request.getMethod())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .cause("Validation Error")
                .message("Exception " + ex.getClass().getSimpleName() + "is happened, check errors field for more details")
                .exception(ex.getClass().getSimpleName())
                .errors(errorBodies)
                .path(request.getRequestURL().toString())
                .build();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public InputElementExceptionBody handleNoSuchElementException(
            EntityNotFoundException ex, HttpServletRequest request
    ) {
        return errorService.handleNoSuchElementException(ex, request);
    }

    @ExceptionHandler(CustomIllegalArgException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public InputElementExceptionBody handleCustomIllegalArgException(
            CustomIllegalArgException ex, HttpServletRequest request
    ) {
        return errorService.handleInvalidArgumentException(ex, request);
    }
}
