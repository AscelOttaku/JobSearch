package kg.attractor.jobsearch.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import kg.attractor.jobsearch.dto.FavoritesDto;
import kg.attractor.jobsearch.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("favorites")
@RequiredArgsConstructor
public class FavoritesController {
    private final FavoritesService favoritesService;

    @PostMapping
    @ResponseStatus(HttpStatus.SEE_OTHER)
    public String saveFavorites(
            @Valid FavoritesDto favoritesDto,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            HttpServletRequest request
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", bindingResult.getGlobalError());
            redirectAttributes.addFlashAttribute("index", favoritesDto.getVacancyId());
            return "redirect:" + request.getHeader("referer");
        }

        favoritesService.saveFavorites(favoritesDto);
        return "redirect:" + request.getHeader("referer");
    }

    @PostMapping("delete/{favoriteId}")
    public String deleteFavorites(
            @PathVariable Long favoriteId,
            HttpServletRequest request
    ) {
        favoritesService.deleteFavoritesById(favoriteId);
        return "redirect:" + request.getHeader("referer");
    }


    @GetMapping("/user")
    public String findUserFavorites(Model model) {
        model.addAttribute("favorites", favoritesService.findALlUserFavorites());
        return "user/favorites";
    }
}
