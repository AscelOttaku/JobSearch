package kg.attractor.jobsearch.exceptions.body;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class ExceptionBody {
    private final LocalDateTime timestamp = LocalDateTime.now();
    private Integer statusCode;
    private String cause;
    private String message;
    private ErrorBody error;
    private String path;
}
