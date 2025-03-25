package kg.attractor.jobsearch.util;

import kg.attractor.jobsearch.exceptions.RespondApplicationNotFoundException;
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

    public static <T> ResponseEntity<T> handleInCaseUserNotFoundAndIllegalArgException(Supplier<T> supplier) {
        try {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } catch (UserNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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

    public static ResponseEntity<Void> handleResumeNotFoundAndIllegalArgException(Runnable runnable) {
        try {
            runnable.run();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (ResumeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static <T> ResponseEntity<T> handleResumeNotFoundException(Supplier<T> supplier) {
        try {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } catch (ResumeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public static <T> ResponseEntity<T> handleIException(Supplier<T> supplier) {
        try {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public static ResponseEntity<Void> handleException(Runnable runnable) {
        try {
            runnable.run();
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public static <T> ResponseEntity<T> handleInCaseRespondApplicationNoFoundAndIllegalArgException(Supplier<T> supplier) {
        try {
            return new ResponseEntity<>(supplier.get(), HttpStatus.OK);
        } catch (RespondApplicationNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
