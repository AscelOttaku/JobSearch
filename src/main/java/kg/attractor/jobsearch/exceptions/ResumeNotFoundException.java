package kg.attractor.jobsearch.exceptions;

import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;

public class ResumeNotFoundException extends EntityNotFoundException {
    public ResumeNotFoundException(String message, CustomBindingResult bindingResult) {
        super(message, bindingResult);
    }
}
