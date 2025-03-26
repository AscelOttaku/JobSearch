package kg.attractor.jobsearch.exceptions.controller_advice;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.exceptions.body.ErrorBody;
import kg.attractor.jobsearch.exceptions.body.ExceptionBody;
import kg.attractor.jobsearch.service.ValidationErrorService;
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
    private final ValidationErrorService validationErrorService;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionBody handleValidationException(
            MethodArgumentNotValidException ex, HttpServletRequest request
    ) {
        List<ErrorBody> errorBodies = validationErrorService.handleValidationException(ex);
        return ExceptionBody.builder()
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .cause(ex.getMessage())
                .message("Validation Error")
                .exception(ex.getClass().getSimpleName())
                .errors(errorBodies)
                .path(request.getRequestURL().toString())
                .build();
    }
}
