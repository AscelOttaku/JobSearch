package kg.attractor.jobsearch.exceptions;

import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;

public class VacancyNotFoundException extends EntityNotFoundException {
    public VacancyNotFoundException(String message, CustomBindingResult bindingResult) {
        super(message, bindingResult);
    }
}
