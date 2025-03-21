package kg.attractor.jobsearch.util;

import kg.attractor.jobsearch.exceptions.ResumeNotFoundException;
import kg.attractor.jobsearch.exceptions.UserNotFoundException;
import kg.attractor.jobsearch.exceptions.VacancyNotFoundException;
import lombok.experimental.UtilityClass;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class ExceptionHandler {

    public static <T> ResponseEntity<T> handleInCaseUserNotFoundException(Supplier<T> supplier) {
        try {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public static <T> Optional<T> handleDataAccessException(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public static <T> ResponseEntity<T> handleVacancyNotFoundAndIllegalArgException(Supplier<T> supplier) {
        try {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (VacancyNotFoundException nfe) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public static <T> ResponseEntity<T> handleResumeNotFoundAndIllegalArgException(Supplier<T> supplier) {
        try {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } catch (ResumeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static <T> ResponseEntity<T> handleIllegalArgumentException(Supplier<T> supplier) {
        try {
            return new ResponseEntity<>(supplier.get(), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
