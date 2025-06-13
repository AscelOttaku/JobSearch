package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.GroupsUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("groups_users")
@RequiredArgsConstructor
public class GroupsUsersController {
    private final GroupsUsersService groupsUsersService;

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
}
