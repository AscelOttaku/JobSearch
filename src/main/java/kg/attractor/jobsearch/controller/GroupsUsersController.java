package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.GroupsService;
import kg.attractor.jobsearch.service.GroupsUsersService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("groups_users")
@RequiredArgsConstructor
public class GroupsUsersController {
    private final GroupsUsersService groupsUsersService;
    private final UserService userService;
    private final GroupsService groupsService;

    @GetMapping("join_group/{groupId}/{userId}")
    public String joinGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        groupsUsersService.joinGroup(groupId, userId);
        return "redirect:/groups_messages/group/" + groupId;
    }

    @GetMapping("join_group/groupId/{groupId}/token/{token}")
    public String joinGroup(@PathVariable Long groupId, @PathVariable String token) {
        groupsUsersService.joinGroupByLink(groupId, token);
        return "redirect:/groups_messages/group/" + groupId;
    }

    @PostMapping("leave_group/{groupId}/{userId}")
    public String leaveGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        groupsUsersService.leaveGroup(groupId, userId);
        return "redirect:/groups";
    }

    @GetMapping("members/{groupId}")
    public String findAllGroupsMembers(@PathVariable Long groupId, Model model) {
        model.addAttribute("members", groupsUsersService.findAllMembersByGroupId(groupId));
        model.addAttribute("admin", userService.findGroupsAdminByGroupId(groupId));
        model.addAttribute("group", groupsService.findGroupsById(groupId));
        return "groups/members";
    }

    @PostMapping("delete/member/{groupId}/{userId}")
    public String deleteGroupMember(@PathVariable Long groupId, @PathVariable Long userId) {
        groupsUsersService.deleteGroupMemberById(groupId, userId);
        return "redirect:/groups_users/members/" + groupId;
    }
}
