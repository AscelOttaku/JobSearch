package kg.attractor.jobsearch.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import kg.attractor.jobsearch.annotations.UniqueCategory;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryDto {
    private Long id;

    @NotNull(message = "{null_message}")
    @NotBlank(message = "{blank_message}")
    @Size(min = 2, max = 50, message = "{category_name_size_message}")
    @Pattern(
            regexp = "^\\p{L}+$",
            message = "{symbol_numbers_pattern_message}"
    )

    @UniqueCategory(message = "category name is already exists")
    private String name;

    private Long parentId;
}
