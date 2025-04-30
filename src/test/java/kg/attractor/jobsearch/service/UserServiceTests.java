package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.UserDto;
import kg.attractor.jobsearch.dto.mapper.impl.UserMapper;
import kg.attractor.jobsearch.model.User;
import kg.attractor.jobsearch.repository.UserRepository;
import kg.attractor.jobsearch.service.impl.UserServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_user_test() {
        User employer = new User();
        employer.setUserId(1L);
        employer.setAccountType("JobSeeker");

        User jobSeeker = new User();
        jobSeeker.setUserId(2L);
        jobSeeker.setAccountType("Employer");

        UserDto jobSeekerDto = UserDto.builder()
                .userId(employer.getUserId())
                .accountType(employer.getAccountType())
                .build();

        UserDto employerDto = UserDto.builder()
                .userId(jobSeeker.getUserId())
                .accountType(jobSeeker.getAccountType())
                .build();

        Mockito.when(userRepository.save(Mockito.any(User.class))).thenAnswer(
                invocation -> {
                    User getUser = invocation.getArgument(0);

                    if (getUser.getAccountType().equals("Employer"))
                        return employer;
                    else
                        return jobSeeker;
                }
        );

        Mockito.when(userMapper.mapToEntity(Mockito.any(UserDto.class))).thenAnswer(
                invocationOnMock -> {
                    if (((UserDto) invocationOnMock.getArgument(0)).getAccountType().equals("Employer"))
                        return employer;
                    else
                        return jobSeeker;
                }
        );

        Mockito.when(userMapper.mapToDto(Mockito.any(User.class))).thenAnswer(
                invocationOnMock -> {
                    User getUser = invocationOnMock.getArgument(0);

                    if (getUser.getAccountType().equals("Employer"))
                        return employerDto;
                    else
                        return jobSeekerDto;
                }
        );

        UserDto userDto = UserDto.builder()
                .userId(1L)
                .accountType("JobSeeker")
                .build();

        UserDto getUserDto = userService.createUser(userDto);

        Assertions.assertThat(getUserDto).isNotNull();
        Assertions.assertThat(getUserDto.getUserId()).isEqualTo(userDto.getUserId());
        Assertions.assertThat(getUserDto.getAccountType()).isEqualTo(userDto.getAccountType());
    }
}
