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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            model.addAttribute("groupsMessagesDto", groupsMessagesDto);
            model.addAttribute("group", groupsService.findGroupsById(groupsMessagesDto.getGroupId()));
            model.addAttribute("membersCount", groupsUsersService.findMembersCountByGroupId(groupsMessagesDto.getGroupId()));
            return "groups/groups_messages";
        }

        groupsMessagesService.createMessage(groupsMessagesDto);
        return "redirect:/groups_messages/group/" + groupsMessagesDto.getGroupId();
    }
}
