package kg.attractor.jobsearch.exceptions;

import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;

public class WorkExperienceNotFoundException extends EntityNotFoundException {
    public WorkExperienceNotFoundException(String message, CustomBindingResult bindingResult) {
        super(message, bindingResult);
    }
}
