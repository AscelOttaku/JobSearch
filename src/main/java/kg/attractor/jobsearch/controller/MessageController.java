package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.MessageDto;
import kg.attractor.jobsearch.service.MessageService;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("messages")
public class MessageController {
    private final MessageService messageService;
    private final VacancyService vacancyService;

    public MessageController(MessageService messageService, VacancyService vacancyService) {
        this.messageService = messageService;
        this.vacancyService = vacancyService;
    }

    @GetMapping("/chat/{resumeId}/{vacancyId}")
    public String chat(
            @PathVariable Long resumeId,
            @PathVariable Long vacancyId,
            Model model
    ) {
        model.addAttribute("messages", messageService.findMessagesByResumeIdAndVacancyId(resumeId, vacancyId));
        model.addAttribute("vacancy", vacancyService.findVacancyById(vacancyId));
        return "messages/chat";
    }

    @GetMapping("/chat/{respondId}")
    public String chat(
            @PathVariable Long respondId,
            Model model
    ) {
        List<MessageDto> messagesByRespondId = messageService.findMessagesByRespondId(respondId);
        model.addAttribute("messages", messagesByRespondId);
        model.addAttribute("vacancy", vacancyService.findVacancyByRespondId(respondId));

        if (messagesByRespondId.isEmpty())
            model.addAttribute("respondId", respondId);

        return "messages/chat";
    }

    @PostMapping
    public String saveMessage(
            @Valid MessageDto messageDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("messages", messageService.findMessagesByResumeIdAndVacancyId(messageDto.getResumeId(), messageDto.getVacancyId()));
            model.addAttribute("vacancy", vacancyService.findVacancyById(messageDto.getVacancyId()));
            return "messages/chat";
        }

        messageService.saveMessage(messageDto);
        return "redirect:/messages/chat/" + messageDto.getRespondedApplicationId();
    }

    @GetMapping("users")
    public String findAllMessagesForEmployer(Model model) {
        model.addAttribute("messages", messageService.findEmployerMessages());
        return "messages/messages_users";
    }

    @GetMapping("job_seeker")
    public String findAllMessagesForJobSeeker(Model model) {
        model.addAttribute("messages", messageService.findJobSeekerMessages());
        return "messages/messages_users";
    }

    @PostMapping("delete/{messageId}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String deleteMessageById(@PathVariable Long messageId, HttpServletRequest request) {
        messageService.deleteMessageById(messageId);
        return "redirect:".concat(request.getHeader("referer"));
    }

    @PostMapping("clear/{respondId}")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String clearAllMessages(@PathVariable Long respondId, HttpServletRequest request) {
        messageService.clearHistory(respondId);
        return "redirect:".concat(request.getHeader("referer"));
    }

    @PostMapping("file")
    public String saveFile(
            @RequestParam(required = false) Long respondId,
            @RequestParam(required = false) Long vacancyId,
            @RequestParam(required = false) Long resumeId,
            MultipartFile file
    ) throws IOException {
        if (respondId != null) {
            messageService.saveFile(respondId, file);
            return "redirect:/messages/chat/" + respondId;
        } else if (vacancyId != null && resumeId != null) {
            Long newRespondId = messageService.saveFile(vacancyId, resumeId, file);
            return "redirect:/messages/chat/" + newRespondId;
        } else {
            throw new IllegalArgumentException("Недостаточно параметров для загрузки файла");
        }
    }
}
