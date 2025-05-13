package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kg.attractor.jobsearch.annotations.IsCategoryNameExists;
import lombok.Builder;
import lombok.Getter;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
public class CategoryDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1;

    private Long id;

    @NotBlank(message = "{blank_message}")
    @Size(min = 2, max = 50, message = "{category_name_size_message}")
    @Pattern(
            regexp = "^\\p{L}+$",
            message = "{symbol_numbers_pattern_message}"
    )

    @IsCategoryNameExists(message = "{category_name_exist_error}")
    private String name;

    private Long parentId;
}
