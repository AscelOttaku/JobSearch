package kg.attractor.jobsearch.exceptions.body;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorBody {
    private String objectName;
    private Object rejectedValue;
    private String fieldName;
    private String message;
}
