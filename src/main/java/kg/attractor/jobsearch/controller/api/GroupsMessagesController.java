package kg.attractor.jobsearch.controller.api;

import kg.attractor.jobsearch.dto.GroupsMessagesDto;
import kg.attractor.jobsearch.service.GroupsMessagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("apiGroupsMessagesController")
@RequestMapping("api/groups_messages")
@RequiredArgsConstructor
public class GroupsMessagesController {
    private final GroupsMessagesService groupsMessagesService;

    @GetMapping("group/row/{groupId}")
    public List<GroupsMessagesDto> findAllGroupsMessageByGroupId(@PathVariable Long groupId) {
        return groupsMessagesService.findALlGroupsMessageByGroupId(groupId);
    }
}
