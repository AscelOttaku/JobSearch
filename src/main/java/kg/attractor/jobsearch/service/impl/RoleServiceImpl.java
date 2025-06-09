package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.RoleDto;
import kg.attractor.jobsearch.dto.mapper.RoleMapper;
import kg.attractor.jobsearch.model.Role;
import kg.attractor.jobsearch.repository.RoleRepository;
import kg.attractor.jobsearch.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;

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
    public String findRoleNameByName(String roleName) {
        Assert.notNull(roleName, "Role name must not be null");

        return roleRepository.findRoleByRoleName(roleName)
                .map(Role::getRoleName)
                .orElseThrow(() -> new NoSuchElementException("Role not found by roleName: " + roleName));
    }
}
