package kg.attractor.jobsearch.exceptions;

import kg.attractor.jobsearch.exceptions.body.CustomBindingResult;

public class CategoryNotFoundException extends EntityNotFoundException {
  public CategoryNotFoundException(String message, CustomBindingResult bindingResult) {
    super(message, bindingResult);
  }
}
