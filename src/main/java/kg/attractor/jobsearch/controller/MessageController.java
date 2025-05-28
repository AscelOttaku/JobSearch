package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.MessageDto;
import kg.attractor.jobsearch.service.MessageService;
import kg.attractor.jobsearch.service.VacancyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
        model.addAttribute("messages", messageService.findMessagesByRespondId(respondId));
        model.addAttribute("vacancy", vacancyService.findVacancyByRespondId(respondId));

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
    public String findAllMessages(Model model) {
        model.addAttribute("messages", messageService.findUserMessages());
        return "messages/messages_users";
    }
}
