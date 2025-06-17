package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.CompanyDto;
import kg.attractor.jobsearch.dto.PageHolder;
import kg.attractor.jobsearch.dto.VacancyDto;
import kg.attractor.jobsearch.dto.mapper.impl.CompanyMapper;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.CompanyService;
import kg.attractor.jobsearch.service.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {
    private final UserRepository userRepository;
    private final VacancyService vacancyService;
    private final CompanyMapper companyMapper;

    @Override
    public PageHolder<CompanyDto> findAllCompanies(int page, int size) {
        Page<User> employersPage = userRepository
                .findUsersByRoleRoleNameIgnoreCases(
                        "employer", PageRequest.of(page, size)
                );

        List<CompanyDto> companies = employersPage.stream()
                .map(companyMapper::mapToDto)
                .toList();

        companies.forEach(companyDto ->
                companyDto.setVacancies(PageHolder.<VacancyDto>builder()
                        .content(vacancyService.findVacanciesByUserId(companyDto.getId()))
                        .build()));

        return PageHolder.<CompanyDto>builder()
                .content(companies)
                .totalPages(employersPage.getTotalPages())
                .size(employersPage.getSize())
                .page(page)
                .hasNextPage(employersPage.hasNext())
                .hasPreviousPage(employersPage.hasPrevious())
                .build();
    }

    @Override
    public CompanyDto findCompanyById(Long id, int page, int size) {
        CompanyDto companyDto = userRepository.findById(id)
                .map(companyMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Company not found"));

        companyDto.setVacancies(vacancyService.findVacanciesByUserId(id, page, size));
        return companyDto;
    }

    @Override
    public String findCompanyAvatarById(Long companyId) {
        return userRepository.findUserAvatarById(companyId)
                .orElseThrow(() -> new NoSuchElementException("Company avatar not found by id " + companyId));
    }

    @Override
    public CompanyDto findCompanyById(Long companyId) {
        return userRepository.findById(companyId)
                .map(companyMapper::mapToDto)
                .orElseThrow(() -> new NoSuchElementException("Company not found by id " + companyId));
    }
}
