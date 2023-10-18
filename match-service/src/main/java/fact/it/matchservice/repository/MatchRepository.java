package fact.it.matchservice.repository;

import fact.it.matchservice.model.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MatchRepository extends MongoRepository<Match, String> {
    List<Match> findByDate(List<String> date);
}
