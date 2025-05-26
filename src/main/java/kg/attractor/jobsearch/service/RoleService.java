package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.RoleDto;

import java.util.List;

public interface RoleService {
    List<RoleDto> findAllRoles();
}
