package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.service.GroupsUsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
