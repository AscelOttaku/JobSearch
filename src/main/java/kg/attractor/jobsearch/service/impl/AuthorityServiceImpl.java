package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.AuthorityDto;
import kg.attractor.jobsearch.dto.mapper.AuthorityMapper;
import kg.attractor.jobsearch.repository.AuthorityRepository;
import kg.attractor.jobsearch.service.AuthorityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorityServiceImpl implements AuthorityService {
    private final AuthorityRepository authorityRepository;
    private final AuthorityMapper authorityMapper;

    @Override
    public List<AuthorityDto> findAllAuthoritiesByRoleId(Long roleId) {
        return authorityRepository.findAllAuthoritiesByRoleId(roleId).stream()
                .map(authorityMapper::mapToDto)
                .toList();
    }
}
