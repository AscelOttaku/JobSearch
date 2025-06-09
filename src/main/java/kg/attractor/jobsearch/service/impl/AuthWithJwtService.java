package kg.attractor.jobsearch.service.impl;

import kg.attractor.jobsearch.dto.JwtAuthenticationResponse;
import kg.attractor.jobsearch.dto.SignInDto;
import kg.attractor.jobsearch.dto.SignUpRequestDto;
import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.service.RoleService;
import kg.attractor.jobsearch.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthWithJwtService {
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequestDto signUpRequestDto) {
        var userDto = UserDto.builder()
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .name(signUpRequestDto.getUsername())
                .accountType(roleService.findRoleNameByName("USER"))
                .build();

        userService.createUser(userDto);

        var jwt = jwtService.generateToken((User) userDetailsService.loadUserByUsername(signUpRequestDto.getEmail()))
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInDto signInDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInDto.getEmail(),
                        signInDto.getPassword()
                )
        );

        var jwt = jwtService.generateToken((User) userDetailsService.loadUserByUsername(signInDto.getEmail()))
        return new JwtAuthenticationResponse(jwt);
    }
}
