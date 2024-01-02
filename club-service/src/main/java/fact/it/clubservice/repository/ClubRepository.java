package fact.it.clubservice.repository;

import fact.it.clubservice.model.Club;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface ClubRepository extends JpaRepository<Club, Long> {
    List<Club> findByNameIn(List<String> name);
    @Query("SELECT c FROM Club c WHERE c.name = :clubName")
    Club findByName(@Param("clubName") String clubName);
}
