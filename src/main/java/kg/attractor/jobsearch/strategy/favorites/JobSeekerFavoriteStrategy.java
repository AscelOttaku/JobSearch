package kg.attractor.jobsearch.strategy.favorites;

import kg.attractor.jobsearch.dto.FavoritesDto;
import kg.attractor.jobsearch.service.FavoritesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobSeekerFavoriteStrategy extends FavoriteStrategy {
    protected JobSeekerFavoriteStrategy(FavoritesService favoritesService) {
        super(favoritesService);
    }

    @Override
    public void saveFavorites(FavoritesDto favoritesDto) {
        favoritesService.saveJobSeekerFavorites(favoritesDto);
    }

    @Override
    public FavoritesDto deleteFavorites(Long favoritesId) {
        return favoritesService.deleteFavoritesForJobSeeker(favoritesId);
    }

    @Override
    public List<FavoritesDto> findAllUserFavorites() {
        return favoritesService.findAllJobSeekerFavorites();
    }

    @Override
    public String getRoleName() {
        return "JOB_SEEKER";
    }
}
