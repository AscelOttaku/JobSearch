package kg.attractor.jobsearch.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Past;
import kg.attractor.jobsearch.annotations.IsDateCorrect;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Data
@IsDateCorrect(
        dateFrom = "startDate", dateTo = "endDate",
        message = "start date should be before end date"
)
public class EducationalInfoDto {
    private Long id;
    private String institution;
    private String program;

    @Past
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;
    private String degree;
}
