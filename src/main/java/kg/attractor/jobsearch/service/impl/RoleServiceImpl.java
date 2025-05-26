package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.RoleDto;
import kg.attractor.jobsearch.dto.mapper.RoleMapper;
import kg.attractor.jobsearch.repository.RoleRepository;
import kg.attractor.jobsearch.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
