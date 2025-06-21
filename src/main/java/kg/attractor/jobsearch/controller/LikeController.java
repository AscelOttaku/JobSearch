package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.LikeDto;
import kg.attractor.jobsearch.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("like")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;

    @PostMapping("create")
    public String like(@Valid LikeDto likeDto) {
        LikeDto like = likeService.like(likeDto);
        return "redirect:/videos/video/" + like.getVideoId();
    }

    @PostMapping("delete/{deleteId}")
    public String dislike(@PathVariable Long deleteId, HttpServletRequest httpServletRequest) {
        likeService.dislike(deleteId);
        return "redirect:" + httpServletRequest.getHeader("referer");
    }
}
