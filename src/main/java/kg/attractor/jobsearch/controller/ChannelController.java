package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.ChannelDto;
import kg.attractor.jobsearch.enums.ChannelAccess;
import kg.attractor.jobsearch.markers.OnCreate;
import kg.attractor.jobsearch.service.ChannelService;
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
@RequestMapping("channel")
@RequiredArgsConstructor
public class ChannelController {
    private final ChannelService channelService;

    @GetMapping("new_channel")
    public String createNewChannel(Model model) {
        model.addAttribute("channelDto", new ChannelDto());
        model.addAttribute("channelAccesses", List.of(ChannelAccess.values()));
        return "channel/new_channel";
    }

    @PostMapping("create")
    public String createChannel(
            @Validated(OnCreate.class) ChannelDto channelDto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("channelDto", channelDto);
            model.addAttribute("channelAccesses", List.of(ChannelAccess.values()));
            return "channel/new_channel";
        }
        ChannelDto channel = channelService.createChannel(channelDto);
        return "redirect:/channel/info/" + channel.getId();
    }

    @GetMapping("update_channel/{channelId}")
    public String updateChannel(@PathVariable Long channelId, Model model) {
        model.addAttribute("channelDto", channelService.findChannelById(channelId));
        model.addAttribute("channelAccesses", List.of(ChannelAccess.values()));
        return "channel/update_channel";
    }

    @PostMapping("update")
    public String updateChannel(
            @Validated(OnCreate.class) ChannelDto channelDto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("channelDto", channelDto);
            model.addAttribute("channelAccesses", ChannelAccess.values());
            return "channel/update_channel";
        }
        channelService.updateChannel(channelDto);
        return "redirect:/channel/info/" + channelDto.getId();
    }

    @GetMapping("info/{channelId}")
    public String findChannelById(@PathVariable Long channelId, Model model) {
        model.addAttribute("channelDto", channelService.findChannelById(channelId));
        return "channel/channel_info";
    }

    @GetMapping
    public String findAuthUserChannel(Model model) {
        model.addAttribute("channelDto", channelService.findAuthUserChannel()
                .orElse(null));
        return "channel/channel_info";
    }
}
