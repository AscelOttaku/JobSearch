package kg.attractor.jobsearch.controller;

import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.GroupsDto;
import kg.attractor.jobsearch.service.GroupsService;
import kg.attractor.jobsearch.util.marks.CreateOn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("groups")
@RequiredArgsConstructor
public class GroupsController {
    private final GroupsService groupsService;

    @GetMapping
    public String findAllGroups(Model model) {
        List<GroupsDto> groups = groupsService.findAllGroups();

        model.addAttribute("groupsDto", groups);
        return "groups/groups";
    }

    @GetMapping("new_groups")
    public String createGroups(Model model) {
        model.addAttribute("groupsDto", new GroupsDto());
        return "groups/new_groups";
    }

    @PostMapping("new_groups")
    public String createGroups(
            @Validated(value = CreateOn.class) GroupsDto groupsDto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupsDto", groupsDto);
            return "groups/new_groups";
        }

        var savedGroup = groupsService.createGroups(groupsDto);
        return "redirect:/groups_messages/group/" + savedGroup.getId();
    }

    @GetMapping("update_groups/{groupsId}")
    public String updateGroups(@PathVariable Long groupsId, Model model) {
        model.addAttribute("groupsDto", groupsService.findGroupsById(groupsId));
        return "groups/update_groups";
    }

    @PostMapping("update_groups")
    public String updateGroups(
            @Valid GroupsDto groupsDto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("groupsDto", groupsDto);
            return "groups/update_groups";
        }

        groupsService.updateGroups(groupsDto);
        return "redirect:/groups_messages/group/" + groupsDto.getId();
    }

    @PostMapping("delete/{groupsId}")
    public String deleteGroupsById(@PathVariable Long groupsId) {
        groupsService.deleteGroupsById(groupsId);
        return "redirect:/groups";
    }
}
