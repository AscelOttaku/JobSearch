package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.FavoritesDto;
import kg.attractor.jobsearch.dto.UserDto;

import java.util.List;

public interface FavoritesService {
    void saveFavorites(FavoritesDto favoritesDto);

    void saveJobSeekerFavorites(FavoritesDto favoritesDto);

    void saveCompanyFavorites(FavoritesDto favoritesDto);

    boolean findFavoritesByUserIdAndVacancyId(Long vacancyId);

    boolean findFavoritesForCompany(FavoritesDto favoritesDto);

    List<FavoritesDto> findAllCompaniesFavorites();

    List<FavoritesDto> findAllJobSeekerFavorites();

    FavoritesDto deleteFavoriteForCompany(Long favoritesId);

    FavoritesDto deleteFavoritesForJobSeeker(Long favoritesId);

    FavoritesDto deleteFavoritesById(Long favoritesId);

    List<FavoritesDto> findALlUserFavorites();
}
