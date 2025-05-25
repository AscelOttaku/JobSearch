package kg.attractor.jobsearch.strategy.favorites;

import kg.attractor.jobsearch.dto.FavoritesDto;
import kg.attractor.jobsearch.service.FavoritesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public abstract class FavoriteStrategy {
    protected final FavoritesService favoritesService;

    protected FavoriteStrategy(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    public abstract void saveFavorites(FavoritesDto favoritesDto);
    public abstract FavoritesDto deleteFavorites(Long favoritesId);
    public abstract List<FavoritesDto> findAllUserFavorites();
    public abstract String getRoleName();
}
