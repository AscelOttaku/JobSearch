package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.exceptions.body.ErrorBody;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

public interface ValidationErrorService {
    List<ErrorBody> handleValidationException(MethodArgumentNotValidException ex);
}
