package fact.it.playerservice.controller;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.service.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/player")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPlayer(@RequestBody PlayerRequest playerRequest) {
        playerService.createPlayer(playerRequest);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerResponse> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PlayerResponse> getPlayers(@RequestParam List<Integer> playerNumber) {
        return playerService.getPlayers(playerNumber);
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public PlayerResponse getPlayer(@RequestParam Integer playerNumber) {
        return playerService.getPlayer(playerNumber);
    }

    @DeleteMapping("/{playerId}")
    @ResponseStatus(HttpStatus.OK)
    public void deletePlayer(@PathVariable String playerId){
        playerService.deletePlayer(playerId);
    }
}
