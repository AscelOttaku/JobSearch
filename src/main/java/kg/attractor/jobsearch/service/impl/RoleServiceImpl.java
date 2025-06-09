package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.RoleDto;
import kg.attractor.jobsearch.dto.mapper.RoleMapper;
import kg.attractor.jobsearch.model.Role;
import kg.attractor.jobsearch.repository.RoleRepository;
import kg.attractor.jobsearch.service.RoleService;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public List<RoleDto> findAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::mapToDto)
                .toList();
    }

    @Override
    public Optional<Role> findRoleById(Long roleId) {
        return roleRepository.findById(roleId);
    }

    @Override
    public Optional<Role> findRoleByName(String name) {
        String roleNameInSnakeCase = Util.convertToSneakyCase(name);
        return roleRepository.findByRoleNameEqualsIgnoreCase(roleNameInSnakeCase);
    }
}
