package kg.attractor.jobsearch.exceptions.body;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Builder
@Getter
public class ErrorBody {
    private String objectName;
    private String fieldName;
    private Object rejectedValue;
    private Map<String, String> errors;
}
