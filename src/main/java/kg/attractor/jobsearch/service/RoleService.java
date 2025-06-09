package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.RoleDto;
import kg.attractor.jobsearch.model.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {
    List<RoleDto> findAllRoles();

    Optional<Role> findRoleById(Long roleId);

    Optional<Role> findRoleByName(String name);
}
