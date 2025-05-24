package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.FavoritesDto;
import kg.attractor.jobsearch.dto.mapper.FavoritesMapper;
import kg.attractor.jobsearch.factory.FavoritesSaveFactory;
import kg.attractor.jobsearch.repository.CompanyFavoritesRepository;
import kg.attractor.jobsearch.repository.JobSeekerFavoritesRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.FavoritesService;
import kg.attractor.jobsearch.strategy.FavoritesSaveStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {
    private final JobSeekerFavoritesRepository jobSeekerFavoritesRepository;
    private final CompanyFavoritesRepository companyFavoritesRepository;
    private final FavoritesMapper favoritesMapper;
    private final FavoritesSaveFactory favoritesSaveFactory;
    private final AuthorizedUserService authorizedUserService;

    @Override
    public void saveFavorites(FavoritesDto favoritesDto) {
        FavoritesSaveStrategy favoritesSaveStrategy = favoritesSaveFactory.createFavoritesSaveStrategy();
        favoritesSaveStrategy.saveFavorites(this, favoritesDto);
    }

    @Override
    public void saveJobSeekerFavorites(FavoritesDto favoritesDto) {
        favoritesDto.setUserId(authorizedUserService.getAuthorizedUserId());
        jobSeekerFavoritesRepository.save(favoritesMapper.mapToJobSeekerFavorites(favoritesDto));
    }

    @Override
    public void saveCompanyFavorites(FavoritesDto favoritesDto) {
        favoritesDto.setUserId(authorizedUserService.getAuthorizedUserId());
        companyFavoritesRepository.save(favoritesMapper.mapToCompanyFavorites(favoritesDto));
    }

    @Override
    public boolean findFavoritesByUserIdAndVacancyId(Long vacancyId) {
        Assert.notNull(vacancyId, "vacancyId must not be null");

        return jobSeekerFavoritesRepository.findFavoriteByUserUserIdAndVacancyId(authorizedUserService.getAuthorizedUserId(), vacancyId)
                .isPresent();
    }

    @Override
    public boolean findFavoritesForCompany(FavoritesDto favoritesDto) {
        Assert.notNull(favoritesDto, "favoritesDto must not be null");

        return companyFavoritesRepository.findFavoritesByUserIdAndVacancyIdAndResumeId(
                        authorizedUserService.getAuthorizedUserId(), favoritesDto.getVacancyId(), favoritesDto.getResumeId()
                )
                .isPresent();
    }
}
