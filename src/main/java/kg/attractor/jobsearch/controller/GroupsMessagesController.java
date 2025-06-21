package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.GroupsDto;
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
        model.addAttribute("groupsMessageDto", new GroupsMessagesDto());
        return "groups/groups_messages";
    }

    @PostMapping("message")
    public String createMessage(
            @Valid GroupsMessagesDto groupsMessagesDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupsMessageDto", new GroupsMessagesDto());
            return getGroupMessagesPage(groupsMessagesDto, bindingResult, model);
        }

        groupsMessagesService.createMessage(groupsMessagesDto);
        return "redirect:/groups_messages/group/" + groupsMessagesDto.getGroupId();
    }

    private String getGroupMessagesPage(GroupsMessagesDto groupsMessagesDto, BindingResult bindingResult, Model model) {
        model.addAttribute("groupsMessagesDto", groupsMessagesService.findALlGroupsMessageByGroupId(groupsMessagesDto.getGroupId()));
        model.addAttribute("group", groupsService.findGroupsById(groupsMessagesDto.getGroupId()));
        model.addAttribute("membersCount", groupsUsersService.findMembersCountByGroupId(groupsMessagesDto.getGroupId()));
        model.addAttribute("bindingResult", bindingResult);
        return "groups/groups_messages";
    }

    @PostMapping("message_file")
    public String createMessageFile(
            @RequestParam Long groupId,
            MultipartFile multipartFile
    ) {
        groupsMessagesService.createMessageFile(groupId, multipartFile);
        return "redirect:/groups_messages/group/" + groupId;
    }

    @GetMapping("update/message/{messageId}")
    public String updateMessage(
            @PathVariable Long messageId,
            Model model
    ) {
        GroupsDto groupsByMessageId = groupsService.findGroupsByMessageId(messageId);
        model.addAttribute("group", groupsByMessageId);
        model.addAttribute("membersCount", groupsUsersService.findMembersCountByGroupId(groupsByMessageId.getId()));
        model.addAttribute("groupsMessagesDto", groupsMessagesService.findALlGroupsMessageByGroupId(groupsByMessageId.getId()));
        model.addAttribute("groupsMessageDto", groupsMessagesService.findGroupsMessageById(messageId));
        return "groups/update_groups_message";
    }

    @PostMapping("update/message")
    public String updateMessage(
            @Valid GroupsMessagesDto groupsMessagesDto,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupsMessageDto", groupsMessagesDto);
            return getGroupMessagesPage(groupsMessagesDto, bindingResult, model);
        }

        groupsMessagesService.updateMessage(groupsMessagesDto);
        return "redirect:/groups_messages/group/" + groupsMessagesDto.getGroupId();
    }

    @PostMapping("delete/{messageId}")
    public String deleteMessage(@PathVariable Long messageId, HttpServletRequest request) {
        groupsMessagesService.deleteMessageById(messageId);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("delete_all/messages/{groupId}")
    public String deleteAllMessages(@PathVariable Long groupId) {
        groupsMessagesService.deleteAllMessagesByGroupId(groupId);
        return "redirect:/groups_messages/group/" + groupId;
    }
}
