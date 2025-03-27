package kg.attractor.jobsearch.exceptions;

import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;
import lombok.Getter;

@Getter
public class CustomIllegalArgException extends RuntimeException {
    private final CustomBindingResult bindingResult;

    public CustomIllegalArgException(String message, CustomBindingResult bindingResult) {
        super(message);
        this.bindingResult = bindingResult;
    }
}
