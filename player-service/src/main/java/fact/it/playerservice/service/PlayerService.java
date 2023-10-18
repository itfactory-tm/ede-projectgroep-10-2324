package fact.it.playerservice.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.repository.PlayerRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;

    @PostConstruct
    public void loadData() {
//        try {
//            URL url = new URL("https://api.publicapis.org/entries");
//
//            URLConnection request = url.openConnection();
//            request.setRequestProperty("Content-Type", "application/json; utf-8");
//
//            JsonParser jp = new JsonParser();
//            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
//            JsonObject rootObj = root.getAsJsonObject();
//            // Get the "entries" array
//            JsonArray entriesArray = rootObj.getAsJsonArray("entries");
//
//            // Iterate through the entries and print the values of the "API" key
//            for (JsonElement entry : entriesArray) {
//                JsonObject entryObject = entry.getAsJsonObject();
//                String apiName = entryObject.get("API").getAsString();
//                System.out.println("API Name: " + apiName);
//            }
//
//        } catch (Exception e){
//            e.printStackTrace();
//        }
        if(playerRepository.count() <= 0){
            Player player = Player.builder()
                .firstName("Jarne")
                .lastName("Dirken")
                .age(21)
                .build();

            Player player1 = Player.builder()
                    .firstName("Sohaib")
                    .lastName("Ibenhajene")
                    .age(21)
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
