package fact.it.playerservice.repository;

import fact.it.playerservice.model.Player;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PlayerRepository extends MongoRepository<Player, String> {
    List<Player>findByFirstName(List<String> firstName);
    List<Player>findByLastName(List<String> lastName);

    List<Player> findByPlayerNumberIn(List<Integer> playerNumber);
    Player findPlayerByPlayerNumber(Integer playerNumber);
}
