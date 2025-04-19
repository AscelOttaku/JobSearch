package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.CompanyDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Role;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyMapper implements Mapper<CompanyDto, User> {
    private final UserService userService;

    @Override
    public CompanyDto mapToDto(User entity) {
        return CompanyDto.builder()
                .name(entity.getName())
                .email(entity.getEmail())
                .phone(entity.getPhoneNumber())
                .build();
    }

    @Override
    public User mapToEntity(CompanyDto dto) {

        UserDto userDto = userService.findUserById(dto.getId());

        Role role = new Role();
        role.setId(2L);

        User user = new User();
        user.setName(dto.getName());
        user.setSurname(userDto.getName());
        user.setAge(userDto.getAge());
        user.setEmail(dto.getEmail());
        user.setPhoneNumber(dto.getPhone());
        user.setPassword(userDto.getPassword());
        user.setAvatar(dto.getAvatar());
        user.setRole(role);
        user.setEnabled(true);
        return user;
    }
}
