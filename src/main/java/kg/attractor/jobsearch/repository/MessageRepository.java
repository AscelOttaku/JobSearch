package kg.attractor.jobsearch.repository;

import kg.attractor.jobsearch.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesByRespondedApplicationId(Long respondId);

    @Query("select m from Message m " +
            "where m.id in (" +
            "  select min(m2.id) from Message m2 " +
            "  where m2.respondedApplication.id in :respondIds " +
            "  group by (m2.respondedApplication.id)" +
            ")")
    List<Message> findAllMessagesByRespondedApplicationIds(List<Long> respondIds);

    @Transactional
    @Modifying
    @Query("delete from Message m where m.respondedApplication.id = :respondApplicationId")
    void deleteMessageByRespondedApplicationId(Long respondApplicationId);
}
