package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.MessageDto;
import kg.attractor.jobsearch.dto.MessageOutputDto;
import kg.attractor.jobsearch.dto.RespondApplicationDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.MessageMapper;
import kg.attractor.jobsearch.enums.MessageType;
import kg.attractor.jobsearch.model.Message;
import kg.attractor.jobsearch.repository.MessageRepository;
import kg.attractor.jobsearch.service.*;
import kg.attractor.jobsearch.util.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final RespondService respondService;
    private final MessageMapper messageMapper;
    private final AuthorizedUserService authorizedUserService;
    private final VacancyService vacancyService;
    private final UserService userService;
    private final ResumeService resumeService;

    @Override
    public MessageDto saveMessage(MessageDto messageDto) {
        if (messageDto.getRespondedApplicationId() == null ||
                !respondService.isRespondExistById(messageDto.getRespondedApplicationId())) {
            Long respondIdByVacancyIdAndResumeId = respondService.findRespondIdByVacancyIdAndResumeId(messageDto.getVacancyId(), messageDto.getResumeId());
            messageDto.setRespondedApplicationId(respondIdByVacancyIdAndResumeId);
        }

        messageDto.setTime(LocalDateTime.now());
        messageDto.setUserDto(authorizedUserService.getAuthorizedUser());
        if (messageDto.getMessageType() == null) messageDto.setMessageType(MessageType.MESSAGES);
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
                .sorted(Comparator.comparing(MessageDto::getTime).reversed())
                .toList();
    }

    @Override
    public List<MessageOutputDto> findEmployerMessages() {
        List<Long> allResponds = respondService.findAllResponsesByEmployerId(authorizedUserService.getAuthorizedUserId())
                .stream()
                .map(RespondApplicationDto::getId)
                .toList();

        return findAllMessagesByRespondedApplicationIds(allResponds);
    }

    @Override
    public List<MessageOutputDto> findJobSeekerMessages() {
        List<Long> allResponds = respondService.findAllResponsesByJobSeekerId(authorizedUserService.getAuthorizedUserId())
                .stream()
                .map(RespondApplicationDto::getId)
                .toList();

        return findAllMessagesByRespondedApplicationIds(allResponds);
    }

    private List<MessageOutputDto> findAllMessagesByRespondedApplicationIds(List<Long> allResponds) {
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
            messageOutputDto.setResumeName(resumeService.findResumeNameByRespondId(messageOutputDto.getRespondedApplicationId()));
        });
        return messageOutputDtos;
    }

    @SneakyThrows
    @Override
    public void deleteMessageById(Long messageId) {
        Assert.isTrue(messageId != null && messageId >= 0, "Message cannot be null or negative");

        Message message = messageRepository.findById(messageId)
                        .orElseThrow(() -> new NoSuchElementException("message not found by id" + messageId));

        if (message.getMessageType().equals(MessageType.IMAGES)) {
            Path path = Path.of("data/photos/" + message.getContent());
            Files.delete(path);
        } else if (message.getMessageType().equals(MessageType.FILE)) {
            Files.delete(Path.of("data/files/" + message.getContent()));
        }

        messageRepository.delete(message);
    }

    @SneakyThrows
    @Override
    public void clearHistory(Long respondApplicationId) {
        Assert.isTrue(respondApplicationId != null && respondApplicationId >= 0, "Message cannot be null or negative");

        List<Message> messages = messageRepository.findMessagesByRespondedApplicationId(respondApplicationId);
        messageRepository.deleteMessageByRespondedApplicationId(respondApplicationId);

        for (Message message : messages) {
            if (message.getMessageType().equals(MessageType.IMAGES))
                Files.delete(Path.of("data/photos/" + message.getContent()));
            else if (message.getMessageType().equals(MessageType.FILE))
                Files.delete(Path.of("data/files/" + message.getContent()));
        }
    }

    @Override
    public void saveFile(Long respondId, MultipartFile multipartFile) {
        Assert.notNull(respondId, "respondId must not be null");
        Assert.notNull(multipartFile, "multipartFile must not be null");

        var fileInfoDto = FileUtil.createFileForMessages(multipartFile);

        saveMessage(MessageDto.builder()
                .userDto(UserDto.builder()
                        .userId(authorizedUserService.getAuthorizedUserId())
                        .build())
                .respondedApplicationId(respondId)
                .content(fileInfoDto.getFilePath())
                .messageType(fileInfoDto.getMessageType())
                .build());
    }

    @Override
    public Long saveFile(Long vacancyId, Long resumeId, MultipartFile multipartFile) throws IOException {
        Assert.isTrue(vacancyId != null && vacancyId > 0, "Vacancy id cannot be null or negative");
        Assert.isTrue(resumeId != null && resumeId > 0, "resume id cannot be null or negative");
        Assert.notNull(multipartFile, "multipart file cannot be null");

        Long respondId = respondService.findRespondIdByVacancyIdAndResumeId(vacancyId, resumeId);
        saveFile(respondId, multipartFile);
        return respondId;
    }
}
