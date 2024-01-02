package fact.it.playerservice.service;

//import com.google.gson.JsonArray;
//import com.google.gson.JsonParser;
import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.repository.PlayerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
//import com.google.gson.JsonElement;
//import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

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
                .playerNumber(1)
                .birthDate(LocalDate.of(2003, 7, 28))
                .build();

            Player player1 = Player.builder()
                .firstName("Sohaib")
                .lastName("Ibenhajene")
                .playerNumber(2)
                .birthDate(LocalDate.of(2003, 9, 25))
                .build();

            Player player2 = Player.builder()
                .firstName("Neymar")
                .lastName("Junior")
                .playerNumber(3)
                .birthDate(LocalDate.of(1992, 2, 5))
                .build();

            playerRepository.save(player);
            playerRepository.save(player1);
            playerRepository.save(player2);
        }
    }

    public void createPlayer(PlayerRequest playerRequest){
        Random random = new Random();
        Player player = Player.builder()
                .firstName(playerRequest.getFirstName())
                .lastName(playerRequest.getLastName())
                .playerNumber(random.nextInt(10000))
                .birthDate(playerRequest.getBirthDate())
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
                .playerNumber(player.getPlayerNumber())
                .firstName(player.getFirstName())
                .lastName(player.getLastName())
                .birthDate(player.getBirthDate())
                .build();
    }

    public List<PlayerResponse> getPlayers(List<Integer> playerNumber) {
        List<Player> players = playerRepository.findByPlayerNumberIn(playerNumber);

        return players.stream().map(this::mapToPlayerResponse).toList();
    }

    public PlayerResponse getPlayer(Integer playerNumber) {
        Player player = playerRepository.findPlayerByPlayerNumber(playerNumber);

        return mapToPlayerResponse(player);
    }
}
