package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    @Modifying
    @Query(value = "DELETE FROM SKILLS " +
            "where SKILLS.ID in (select s.ID from SKILLS s  " +
            "    left join RESUMES_SKILLS rs on rs.SKILL_ID = s.ID " +
            "where rs.RESUME_ID is null" +
            " )",
            nativeQuery = true)
    void deleteUnusedElements();

    @Query(value = "select s.* from SKILLS as s " +
            "left join RESUMES_SKILLS rs on rs.SKILL_ID = s.ID " +
            "where rs.RESUME_ID is null", nativeQuery = true)
    List<Skill> findUnusedSKills();

    Optional<Skill> findBySkillName(String skillName);
}
