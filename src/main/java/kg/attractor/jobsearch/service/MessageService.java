package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.MessageDto;
import kg.attractor.jobsearch.dto.MessageOutputDto;

import java.util.List;

public interface MessageService {
    MessageDto saveMessage(MessageDto messageDto);

    List<MessageDto> findMessagesByRespondId(Long respondId);

    List<MessageDto> findMessagesByResumeIdAndVacancyId(Long resumeId, Long vacancyId);

    List<MessageOutputDto> findUserMessages();
}
