package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.MessageDto;
import kg.attractor.jobsearch.dto.MessageOutputDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface MessageService {
    MessageDto saveMessage(MessageDto messageDto);

    List<MessageDto> findMessagesByRespondId(Long respondId);

    List<MessageDto> findMessagesByResumeIdAndVacancyId(Long resumeId, Long vacancyId);

    List<MessageOutputDto> findEmployerMessages();

    List<MessageOutputDto> findJobSeekerMessages();

    void deleteMessageById(Long messageId);

    void clearHistory(Long respondApplicationId);

    void saveFile(Long respondId, MultipartFile multipartFile) throws IOException;

    Long saveFile(Long vacancyId, Long resumeId, MultipartFile multipartFile) throws IOException;
}
