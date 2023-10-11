package fact.it.playerservice.service;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.repository.PlayerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    @PostConstruct
    public void loadData() {
        if(playerRepository.count() <= 0){
            Player player = Player.builder()
                    .firstName("Jarne")
                    .lastName("Dirken")
                    .age(21)
                    .build();

            Player player1 = Player.builder()
                    .firstName("Sohaib")
                    .lastName("Ibenhajene")
                    .age(22)
                    .build();

            playerRepository.save(player);
            playerRepository.save(player1);
        }
    }

    public void createPlayer(PlayerRequest playerRequest){
        Player player = Player.builder()
                .firstName(playerRequest.getFirstName())
                .lastName(playerRequest.getLastName())
                .age(playerRequest.getAge())
                .build();

        playerRepository.save(player);
    }

    public List<PlayerResponse> getAllPlayers() {
        List<Player> players = playerRepository.findAll();

        return players.stream().map(this::mapToPlayerResponse).toList();
    }

    public void deletePlayer(String playerId){
        if(playerRepository.existsById(playerId)){
            playerRepository.deleteById(playerId);
        }
    }

    private PlayerResponse mapToPlayerResponse(Player player) {
        return PlayerResponse.builder()
                .id(player.getId())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .age(player.getAge())
                .build();
    }
}
