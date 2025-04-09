package kg.attractor.jobsearch.service;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.InputElementExceptionBody;
import kg.attractor.jobsearch.exceptions.body.ValidationExceptionBody;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

import java.time.format.DateTimeParseException;
import java.util.Map;

public interface ErrorService {
    ValidationExceptionBody handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest httpServletRequest);

    InputElementExceptionBody handleNoSuchElementException(
            EntityNotFoundException elementNotFoundException, HttpServletRequest servletRequest
    );

    InputElementExceptionBody handleInvalidArgumentException(
            CustomIllegalArgException e, HttpServletRequest servletRequest
    );

    Map<String, Object> handleMethodValidationException(HandlerMethodValidationException ex, HttpServletRequest request);

    Map<String, Object> handleDateTimeParserException(DateTimeParseException ex);
}
