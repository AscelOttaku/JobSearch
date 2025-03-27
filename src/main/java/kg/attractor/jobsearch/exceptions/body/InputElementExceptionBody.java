package kg.attractor.jobsearch.exceptions.body;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Builder
@Getter
public class InputElementExceptionBody {
    private final String timestamp = DateTimeFormatter
            .ofPattern("yyyy-MM-dd HH:mm:ss")
            .format(LocalDateTime.now());

    private int status;
    private String error;
    private String method;
    private String message;
    private CustomBindingResult bindingResult;
    private String path;
}
