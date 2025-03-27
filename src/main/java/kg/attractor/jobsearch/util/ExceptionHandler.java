package kg.attractor.jobsearch.util;

import lombok.experimental.UtilityClass;
import org.springframework.dao.DataAccessException;

import java.util.Optional;
import java.util.function.Supplier;

@UtilityClass
public class ExceptionHandler {

    public static <T> Optional<T> handleDataAccessException(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }
}
