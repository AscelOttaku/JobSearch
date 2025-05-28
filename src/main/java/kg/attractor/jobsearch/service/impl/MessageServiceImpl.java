package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.MessageDto;
import kg.attractor.jobsearch.dto.MessageOutputDto;
import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.mapper.MessageMapper;
import kg.attractor.jobsearch.model.Message;
import kg.attractor.jobsearch.repository.MessageRepository;
import kg.attractor.jobsearch.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final RespondService respondService;
    private final MessageMapper messageMapper;
    private final AuthorizedUserService authorizedUserService;
    private final VacancyService vacancyService;
    private final UserService userService;

    @Override
    public MessageDto saveMessage(MessageDto messageDto) {
        if (messageDto.getRespondedApplicationId() == null ||
                !respondService.isRespondExistById(messageDto.getRespondedApplicationId())) {
            Long respondIdByVacancyIdAndResumeId = respondService.findRespondIdByVacancyIdAndResumeId(messageDto.getVacancyId(), messageDto.getResumeId());
            messageDto.setRespondedApplicationId(respondIdByVacancyIdAndResumeId);
        }

        messageDto.setTime(LocalDateTime.now());
        messageDto.setUserDto(authorizedUserService.getAuthorizedUser());
        Message save = messageRepository.save(messageMapper.mapToEntity(messageDto));
        return messageMapper.mapToDto(save);
    }

    @Override
    public List<MessageDto> findMessagesByRespondId(Long respondId) {
        Assert.notNull(respondId, "respondId must not be null");

        return messageRepository.findMessagesByRespondedApplicationId(respondId)
                .stream()
                .map(messageMapper::mapToDto)
                .sorted(Comparator.comparing(MessageDto::getTime).reversed())
                .toList();
    }

    @Override
    public List<MessageDto> findMessagesByResumeIdAndVacancyId(Long resumeId, Long vacancyId) {
        Assert.isTrue(resumeId != null && vacancyId != null, "resumeId and vacancyId must not be null");
        Long respondIdByVacancyIdAndResumeId = respondService.findRespondIdByVacancyIdAndResumeId(vacancyId, resumeId);

        return messageRepository.findMessagesByRespondedApplicationId(respondIdByVacancyIdAndResumeId)
                .stream()
                .map(messageMapper::mapToDto)
                .toList();
    }

    @Override
    public List<MessageOutputDto> findUserMessages() {
        List<Long> allResponds = respondService.findAllResponsesByUserId(authorizedUserService.getAuthorizedUserId())
                .stream()
                .map(RespondApplicationDto::getId)
                .toList();

        List<MessageOutputDto> messageOutputDtos = messageRepository.findAllMessagesByRespondedApplicationIds(allResponds)
                .stream()
                .map(message -> MessageOutputDto.builder()
                        .id(message.getId())
                        .respondedApplicationId(message.getRespondedApplication().getId())
                        .build())
                .toList();

        messageOutputDtos.forEach(messageOutputDto -> {
            messageOutputDto.setUserDto(userService.findUserByRespondId(messageOutputDto.getRespondedApplicationId()));
            messageOutputDto.setVacancyDto(vacancyService.findVacancyByRespondId(messageOutputDto.getRespondedApplicationId()));
        });

        return messageOutputDtos;
    }
}
