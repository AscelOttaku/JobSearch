package kg.attractor.jobsearch.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.InputElementExceptionBody;
import kg.attractor.jobsearch.exceptions.body.ValidationErrorBody;
import kg.attractor.jobsearch.service.ErrorService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ErrorServiceImpl implements ErrorService {

    @Override
    public List<ValidationErrorBody> handleValidationException(MethodArgumentNotValidException ex) {
        List<ValidationErrorBody> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            FieldError fieldError = (FieldError) error;
            String message = fieldError.getDefaultMessage();
            String objectName = fieldError.getObjectName();
            String fieldName = fieldError.getField();
            Object rejectedValue = fieldError.getRejectedValue();

            errors.add(ValidationErrorBody
                    .builder()
                            .objectName(objectName)
                            .rejectedValue(rejectedValue)
                            .fieldName(fieldName)
                            .message(message)
                    .build());
        });

        return errors;
    }

    @Override
    public InputElementExceptionBody handleNoSuchElementException(
            EntityNotFoundException elementNotFoundException, HttpServletRequest servletRequest
    ) {
        return InputElementExceptionBody.builder()
                .status(HttpStatus.NOT_FOUND.value())
                .method(servletRequest.getMethod())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(elementNotFoundException.getMessage())
                .bindingResult(elementNotFoundException.getBindingResult())
                .path(servletRequest.getRequestURI())
                .build();
    }

    @Override
    public InputElementExceptionBody handleInvalidArgumentException(
            CustomIllegalArgException e, HttpServletRequest servletRequest
    ) {
        return InputElementExceptionBody.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .method(servletRequest.getMethod())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(e.getMessage())
                .bindingResult(e.getBindingResult())
                .path(servletRequest.getRequestURI())
                .build();
    }
}
