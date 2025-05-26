package kg.attractor.jobsearch.strategy.favorites;

import kg.attractor.jobsearch.dto.FavoritesDto;
import kg.attractor.jobsearch.service.FavoritesService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyFavoriteStrategy extends FavoriteStrategy {
    protected CompanyFavoriteStrategy(FavoritesService favoritesService) {
        super(favoritesService);
    }

    @Override
    public void saveFavorites(FavoritesDto favoritesDto) {
        favoritesService.saveCompanyFavorites(favoritesDto);
    }

    @Override
    public FavoritesDto deleteFavorites(Long favoritesId) {
        return favoritesService.deleteFavoriteForCompany(favoritesId);
    }

    @Override
    public List<FavoritesDto> findAllUserFavorites() {
        return favoritesService.findAllCompaniesFavorites();
    }

    @Override
    public String getRoleName() {
        return "EMPLOYER";
    }
}
