package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.WorkExperienceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperienceInfo, Long> {
    List<WorkExperienceInfo> findWorkExperienceInfoByResumeId(Long resumeId);
}
