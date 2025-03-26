package kg.attractor.jobsearch.exceptions.body;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Builder
@Getter
public class ExceptionBody {
    private final String timestamp = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .format(LocalDateTime.now());

    private int statusCode;
    private String cause;
    private String message;
    private String exception;
    private List<ErrorBody> errors;
    private String path;
}
