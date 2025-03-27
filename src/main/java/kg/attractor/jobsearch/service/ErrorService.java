package kg.attractor.jobsearch.service;

import jakarta.servlet.http.HttpServletRequest;
import kg.attractor.jobsearch.exceptions.CustomIllegalArgException;
import kg.attractor.jobsearch.exceptions.EntityNotFoundException;
import kg.attractor.jobsearch.exceptions.body.InputElementExceptionBody;
import kg.attractor.jobsearch.exceptions.body.ValidationErrorBody;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public interface ErrorService {
    List<ValidationErrorBody> handleValidationException(MethodArgumentNotValidException ex);

    InputElementExceptionBody handleNoSuchElementException(
            EntityNotFoundException elementNotFoundException, HttpServletRequest servletRequest
    );

    InputElementExceptionBody handleInvalidArgumentException(
            CustomIllegalArgException e, HttpServletRequest servletRequest
    );
}
