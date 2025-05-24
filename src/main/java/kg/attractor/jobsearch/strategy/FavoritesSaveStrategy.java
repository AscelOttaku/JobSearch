package kg.attractor.jobsearch.strategy;

import kg.attractor.jobsearch.dto.FavoritesDto;
import kg.attractor.jobsearch.service.FavoritesService;

public enum FavoritesSaveStrategy {
    COMPANY {
        @Override
        public void saveFavorites(FavoritesService favoritesService, FavoritesDto favoritesDto) {
            favoritesService.saveCompanyFavorites(favoritesDto);
        }

        @Override
        public String getRoleName() {
            return "EMPLOYER";
        }
    },
    JOB_SEEKER {
        @Override
        public void saveFavorites(FavoritesService favoritesService, FavoritesDto favoritesDto) {
            favoritesService.saveJobSeekerFavorites(favoritesDto);
        }

        @Override
        public String getRoleName() {
            return "JOB_SEEKER";
        }
    };

    public abstract void saveFavorites(FavoritesService favoritesService, FavoritesDto favoritesDto);
    public abstract String getRoleName();
}
