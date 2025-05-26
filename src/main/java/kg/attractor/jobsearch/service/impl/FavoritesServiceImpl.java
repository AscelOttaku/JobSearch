package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.FavoritesDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.FavoritesMapper;
import kg.attractor.jobsearch.factory.FavoritesFactory;
import kg.attractor.jobsearch.model.CompanyFavorites;
import kg.attractor.jobsearch.model.JobSeekerFavorites;
import kg.attractor.jobsearch.repository.CompanyFavoritesRepository;
import kg.attractor.jobsearch.repository.JobSeekerFavoritesRepository;
import kg.attractor.jobsearch.service.AuthorizedUserService;
import kg.attractor.jobsearch.service.FavoritesService;
import kg.attractor.jobsearch.strategy.favorites.FavoriteStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoritesServiceImpl implements FavoritesService {
    private final JobSeekerFavoritesRepository jobSeekerFavoritesRepository;
    private final CompanyFavoritesRepository companyFavoritesRepository;
    private final FavoritesMapper favoritesMapper;
    private FavoritesFactory favoritesFactory;
    private final AuthorizedUserService authorizedUserService;

    @Autowired
    public void setFavoritesFactory(@Lazy FavoritesFactory favoritesFactory) {
        this.favoritesFactory = favoritesFactory;
    }

    @Override
    public void saveFavorites(FavoritesDto favoritesDto) {
        UserDto authUserDto = authorizedUserService.getAuthorizedUser();
        FavoriteStrategy favoritesStrategy = favoritesFactory.createFavoritesStrategy(authUserDto.getAccountType());
        favoritesStrategy.saveFavorites(favoritesDto);
    }

    @Override
    public void saveJobSeekerFavorites(FavoritesDto favoritesDto) {
        Assert.notNull(favoritesDto.getVacancyId(), "Vacancy id cannot be null");

        favoritesDto.setUserId(authorizedUserService.getAuthorizedUserId());
        jobSeekerFavoritesRepository.save(favoritesMapper.mapToJobSeekerFavorites(favoritesDto));
    }

    @Override
    public void saveCompanyFavorites(FavoritesDto favoritesDto) {
        Assert.isTrue(favoritesDto.getVacancyId() != null || favoritesDto.getId() != null,
                "vacancyId or resumeId are required");

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
        return companyFavoritesRepository.findFavoritesByUserIdAndVacancyIdAndResumeId(
                        authorizedUserService.getAuthorizedUserId(), favoritesDto.getVacancyId() , favoritesDto.getResumeId()
                )
                .isPresent();
    }

    @Override
    public List<FavoritesDto> findAllCompaniesFavorites() {
        UserDto authUserDto = authorizedUserService.getAuthorizedUser();
        Assert.isTrue(authUserDto.getAccountType().equals("EMPLOYER"), "auth user must be employer");

        return companyFavoritesRepository.findAllCompaniesFavoritesByUserId(authUserDto.getUserId())
                .stream()
                .map(favoritesMapper::mapToDto)
                .toList();
    }

    @Override
    public List<FavoritesDto> findAllJobSeekerFavorites() {
        UserDto authUserDto = authorizedUserService.getAuthorizedUser();
        Assert.isTrue(authUserDto.getAccountType().equals("JOB_SEEKER"), "auth user must be job_seeker");

        return jobSeekerFavoritesRepository.findAllJobSeekerFavoritesByUserId(authUserDto.getUserId())
                .stream()
                .map(favoritesMapper::mapToDto)
                .toList();
    }

    @Override
    public FavoritesDto deleteFavoriteForCompany(Long favoritesId) {
        Assert.notNull(favoritesId, "favoritesDto must not be null");

        CompanyFavorites favoriteDoNotExists = companyFavoritesRepository.findById(favoritesId)
                .orElseThrow(() -> new IllegalArgumentException("favorite do not exists"));

        companyFavoritesRepository.delete(favoriteDoNotExists);
        return favoritesMapper.mapToDto(favoriteDoNotExists);
    }

    @Override
    public FavoritesDto deleteFavoritesForJobSeeker(Long favoritesId) {
        Assert.notNull(favoritesId, "favoritesDto must not be null");

        JobSeekerFavorites jobSeekerFavorites = jobSeekerFavoritesRepository.findById(favoritesId)
                .orElseThrow(() -> new IllegalArgumentException("favorite do not exists"));

        jobSeekerFavoritesRepository.delete(jobSeekerFavorites);
        return favoritesMapper.mapToDto(jobSeekerFavorites);
    }

    @Override
    public FavoritesDto deleteFavoritesById(Long favoritesId) {
        Assert.notNull(favoritesId, "favoritesDto must not be null");

        UserDto authUserDto = authorizedUserService.getAuthorizedUser();
        FavoriteStrategy favoritesStrategy = favoritesFactory.createFavoritesStrategy(authUserDto.getAccountType());
        return favoritesStrategy.deleteFavorites(favoritesId);
    }

    @Override
    public List<FavoritesDto> findALlUserFavorites() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken)
            return Collections.emptyList();

        String roleName = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(role -> role.equals("EMPLOYER") || role.equals("JOB_SEEKER"))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No Authority Found"));

        FavoriteStrategy favoriteStrategy = favoritesFactory.createFavoritesStrategy(roleName);
        return favoriteStrategy.findAllUserFavorites();
    }
}
