package kg.attractor.jobsearch.service;

import kg.attractor.jobsearch.dto.ResumeDto;

import java.util.List;

public interface ResumeRespondService {
    List<ResumeDto> findAllRespondedResumes();
}
