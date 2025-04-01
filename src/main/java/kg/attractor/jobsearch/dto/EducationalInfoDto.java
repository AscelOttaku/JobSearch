package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@EqualsAndHashCode
public class EducationalInfoDto {
    private Long id;
    private String institution;
    private String program;

    @Past
    private LocalDateTime startDate;

    private LocalDateTime endDate;
    private String degree;

    @AssertTrue(message = "Start date cannot be after end")
    private boolean isEndDateAfterStartDate() {
        return endDate.isAfter(startDate);
    }
}
