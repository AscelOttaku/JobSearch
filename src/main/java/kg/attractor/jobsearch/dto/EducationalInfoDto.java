package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import kg.attractor.jobsearch.annotations.IsDateCorrect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@Data
@IsDateCorrect(
        dateFrom = "startDate", dateTo = "endDate",
        message = "start date should be before end date"
)
@AllArgsConstructor
@NoArgsConstructor
public class EducationalInfoDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    private Long id;
    private Long resumeId;

    @NotBlank(message = "{blank_message}")
    private String institution;

    @NotBlank(message = "{blank_message}")
    private String program;

    @NotNull(message = "{null_message}")
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    @NotBlank(message = "{blank_message}")
    private String degree;
}
