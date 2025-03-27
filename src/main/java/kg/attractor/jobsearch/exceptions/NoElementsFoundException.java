package kg.attractor.jobsearch.exceptions;

import java.util.NoSuchElementException;

public class NoElementsFoundException extends NoSuchElementException {
    public NoElementsFoundException(String message) {
        super(message);
    }
}
