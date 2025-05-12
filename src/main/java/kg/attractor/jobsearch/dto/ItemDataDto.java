package kg.attractor.jobsearch.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ItemDataDto {
    private List<ItemDto> items;

    @Getter
    @Setter
    public static class ItemDto {
        private Long id;
        private String text;
    }
}
