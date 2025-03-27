package kg.attractor.jobsearch.exceptions;

import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;

public class RespondApplicationNotFoundException extends EntityNotFoundException {
    public RespondApplicationNotFoundException(String message, CustomBindingResult bindingResult) {
        super(message, bindingResult);
    }
}
