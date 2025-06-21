package kg.attractor.jobsearch.annotations.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import kg.attractor.jobsearch.annotations.UniqueFavorites;
import kg.attractor.jobsearch.dto.FavoritesDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.FavoritesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UniqueFavoritesValidator implements ConstraintValidator<UniqueFavorites, FavoritesDto> {
    private final FavoritesService favoritesService;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public boolean isValid(FavoritesDto favoritesDto, ConstraintValidatorContext constraintValidatorContext) {
        UserDto userDto = authorizedUserService.getAuthorizedUser();

        if (userDto.getAccountType().equals("EMPLOYER"))
            return !favoritesService.findFavoritesForCompany(favoritesDto);

        return !favoritesService.findFavoritesByUserIdAndVacancyId(favoritesDto.getVacancyId());
    }
}
