package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.GroupsMessagesDto;
import kg.attractor.jobsearch.service.GroupsMessagesService;
import kg.attractor.jobsearch.service.GroupsService;
import kg.attractor.jobsearch.service.GroupsUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("groups_messages")
@RequiredArgsConstructor
public class GroupsMessagesController {
    private final GroupsMessagesService groupsMessagesService;
    private final GroupsService groupsService;
    private final GroupsUsersService groupsUsersService;

    @GetMapping("group/{groupId}")
    public String findAllGroupsMessageByGroupId(@PathVariable Long groupId, Model model) {
        model.addAttribute("group", groupsService.findGroupsById(groupId));
        model.addAttribute("membersCount", groupsUsersService.findMembersCountByGroupId(groupId));
        model.addAttribute("groupsMessagesDto", groupsMessagesService.findALlGroupsMessageByGroupId(groupId));
        return "groups/groups_messages";
    }

    @PostMapping("message")
    public String createMessage(
            @Valid GroupsMessagesDto groupsMessagesDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupsMessagesDto", groupsMessagesService.findALlGroupsMessageByGroupId(groupsMessagesDto.getGroupId()));
            model.addAttribute("group", groupsService.findGroupsById(groupsMessagesDto.getGroupId()));
            model.addAttribute("membersCount", groupsUsersService.findMembersCountByGroupId(groupsMessagesDto.getGroupId()));
            model.addAttribute("bindingResult", bindingResult);
            return "groups/groups_messages";
        }

        groupsMessagesService.createMessage(groupsMessagesDto);
        return "redirect:/groups_messages/group/" + groupsMessagesDto.getGroupId();
    }

    @PostMapping("message_file")
    public String createMessageFile(
            @RequestParam Long groupId,
            MultipartFile multipartFile
    ) {
        groupsMessagesService.createMessageFile(groupId, multipartFile);
        return "redirect:/groups_messages/group/" + groupId;
    }
}
