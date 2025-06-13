package kg.attractor.jobsearch.dto;

import kg.attractor.jobsearch.enums.MessageType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FIleInfoDto {
    private String filePath;
    private MessageType messageType;
}
