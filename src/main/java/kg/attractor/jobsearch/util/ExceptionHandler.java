package kg.attractor.jobsearch.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;

import java.util.Optional;
import java.util.function.Supplier;

@Slf4j
@UtilityClass
public class ExceptionHandler {

    public static <T> Optional<T> handleDataAccessException(Supplier<T> supplier) {
        try {
            return Optional.of(supplier.get());
        } catch (DataAccessException e) {
            log.warn(e.getMessage());
            return Optional.empty();
        }
    }
}
