package kg.attractor.jobsearch.controller;

import kg.attractor.jobsearch.dto.VideoDto;
import kg.attractor.jobsearch.service.ChannelService;
import kg.attractor.jobsearch.service.LikeService;
import kg.attractor.jobsearch.service.VideoService;
import kg.attractor.jobsearch.util.marks.CreateOn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
@RequestMapping("videos")
@RequiredArgsConstructor
public class VideoController {
    private final VideoService videoService;
    private final ChannelService channelService;
    private final LikeService likeService;

    @GetMapping("new_video/{channelId}")
    public String newVideo(@PathVariable Long channelId, Model model) {
        model.addAttribute("videoDto", VideoDto.builder()
                .channelDto(channelService.findChannelById(channelId))
                .build());
        return "video/new_video";
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String createVideo(
            @Validated(CreateOn.class) VideoDto videoDto,
            BindingResult bindingResult,
            Model model
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            videoDto.setChannelDto(channelService.findChannelById(videoDto.getChannelDto().getId()));
            model.addAttribute("videoDto", videoDto);
            return "video/new_video";
        }
        VideoDto video = videoService.createVideo(videoDto);
        return "redirect:/videos/video/" + video.getId();
    }

    @GetMapping("channel/{channelId}")
    public String findChannelVideos(
            @PathVariable Long channelId,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            Model model
    ) {
        model.addAttribute("videos", videoService.findVideosByChannelId(channelId, page, size));
        return "video/channel_videos";
    }

    @GetMapping("video/{videoId}")
    public String findVideo(@PathVariable Long videoId, Model model) {
        model.addAttribute("video", videoService.findVideoById(videoId));
        model.addAttribute("like", likeService.findAuthUserLikeByVideoId(videoId)
                .orElse(null));
        model.addAttribute("likeQuantity", likeService.findAllLikesByVideoId(videoId));
        return "video/video";
    }
}
