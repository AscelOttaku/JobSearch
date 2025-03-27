package kg.attractor.jobsearch.exceptions;

import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;

public class EducationInfoNotFoundException extends EntityNotFoundException {
    public EducationInfoNotFoundException(String message, CustomBindingResult bindingResult) {
        super(message, bindingResult);
    }
}
