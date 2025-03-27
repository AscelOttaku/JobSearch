package kg.attractor.jobsearch.exceptions;

import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;

public class UserNotFoundException extends EntityNotFoundException {
    public UserNotFoundException(String message, CustomBindingResult bindingResult) {
        super(message, bindingResult);
    }
}
