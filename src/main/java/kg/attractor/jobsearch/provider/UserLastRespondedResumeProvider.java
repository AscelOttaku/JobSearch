package kg.attractor.jobsearch.provider;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.Optional;

public interface UserLastRespondedResumeProvider {
    Optional<ResumeDto> findUserLastRespondedResume();
}
