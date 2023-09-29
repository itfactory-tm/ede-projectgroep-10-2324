package fact.it.clubservice.repository;

import fact.it.clubservice.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, String> {
}
