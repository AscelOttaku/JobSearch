package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.exceptions.body.ErrorBody;
import kg.attractor.jobsearch.service.ValidationErrorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValidationErrorServiceImpl implements ValidationErrorService {

    @Override
    public List<ErrorBody> handleValidationException(MethodArgumentNotValidException ex) {
        List<ErrorBody> errors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            FieldError fieldError = (FieldError) error;
            String message = fieldError.getDefaultMessage();
            String objectName = fieldError.getObjectName();
            String fieldName = fieldError.getField();
            Object rejectedValue = fieldError.getRejectedValue();

            errors.add(ErrorBody
                    .builder()
                            .objectName(objectName)
                            .rejectedValue(rejectedValue)
                            .fieldName(fieldName)
                            .message(message)
                    .build());
        });

        return errors;
    }
}
