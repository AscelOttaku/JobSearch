package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.FavoritesDto;

public interface FavoritesService {
    void saveFavorites(FavoritesDto favoritesDto);

    void saveJobSeekerFavorites(FavoritesDto favoritesDto);

    void saveCompanyFavorites(FavoritesDto favoritesDto);

    boolean findFavoritesByUserIdAndVacancyId(Long vacancyId);

    boolean findFavoritesForCompany(FavoritesDto favoritesDto);
}
