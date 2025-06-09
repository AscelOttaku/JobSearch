package kg.attractor.jobsearch.dto.mapper.impl;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.Mapper;
import kg.attractor.jobsearch.model.Role;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.service.RoleService;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserMapper implements Mapper<UserDto, User> {
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Override
    public UserDto mapToDto(User user) {
        return UserDto.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .surname(user.getSurname())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .phoneNumber(user.getPhoneNumber())
                .avatar(user.getAvatar())
                .accountType(user.getRole().getRoleName())
                .build();
    }

    @Override
    public User mapToEntity(UserDto userDto) {
        User user = new User();
        user.setUserId(userDto.getUserId());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setAge(userDto.getAge());
        user.setEmail(userDto.getEmail());
        user.setPassword(Util.encodePassword(passwordEncoder, userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setAccountType(userDto.getAccountType());
        user.setAvatar(userDto.getAvatar());

        Long roleId = Objects.equals(userDto.getAccountType(), "EMPLOYER") ? 1L : 2L;
        Role role = roleService.findRoleById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));
        user.setRole(role);
        user.setEnabled(true);
        return user;
    }
}
