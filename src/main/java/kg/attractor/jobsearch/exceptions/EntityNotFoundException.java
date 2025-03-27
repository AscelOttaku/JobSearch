package kg.attractor.jobsearch.exceptions;

import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import lombok.Getter;

import java.util.NoSuchElementException;

@Getter
public class EntityNotFoundException extends NoSuchElementException {
    private final CustomBindingResult bindingResult;

    public EntityNotFoundException(String message, CustomBindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }
}
