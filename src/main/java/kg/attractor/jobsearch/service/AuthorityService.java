package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.AuthorityDto;

import java.util.List;

public interface AuthorityService {
    List<AuthorityDto> findAllAuthoritiesByRoleId(Long roleId);
}
