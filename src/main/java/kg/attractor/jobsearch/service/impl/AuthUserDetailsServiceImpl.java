package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.model.Authority;
import kg.attractor.jobsearch.model.Role;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.RoleRepository;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username)
                .orElseGet(() -> userRepository.findUserByPhoneNumber(username)
                        .orElseThrow(() -> new UsernameNotFoundException(
                                "User not found by email or phone number: " + username)
                        )
                );

        log.info(user.getEmail());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRole())
        );
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Role roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<GrantedAuthority> getGrantedAuthorities(Collection<String> privileges) {
        return privileges.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    private List<String> getPrivileges(Role role) {
        List<String> privileges = new ArrayList<>();

        privileges.add(role.getRoleName());
        List<Authority> authorities = new ArrayList<>(role.getAuthorities());

        authorities.forEach(authority -> privileges.add(authority.getAuthorityName()));

        return privileges;
    }

    public void processOAuthPostLogin(Map<String, Object> attributes) {
        String email = (String) attributes.get("email");
        String avatar = (String) attributes.get("picture");

        var existingUser = userRepository.findUserByEmail(email);

        if (existingUser.isEmpty()) {
            var user = new User();
            user.setName((String) attributes.get("given_name"));
            user.setSurname((String) attributes.get("family_name"));
            user.setEmail(email);
            user.setPassword(encoder.encode(Util.generateUniqueValue()));
            user.setRole(roleRepository.findByRoleNameEqualsIgnoreCase("JOB_SEEKER")
                    .orElseThrow(() -> new NoSuchElementException("role not found by name " + "JOB_SEEKER")));
            user.setEnabled(Boolean.TRUE);
            user.setAvatar(avatar);

            userRepository.save(user);
        }

        UserDetails userDetails = loadUserByUsername(email);
        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(auth);
    }
}
