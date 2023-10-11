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
    public void createPlayer(@RequestBody PlayerRequest playerRequest) {
        playerService.createPlayer(playerRequest);
    }

    @GetMapping("/all")
    public List<PlayerResponse> getAllPlayers() {
        return playerService.getAllPlayers();
    }

    @DeleteMapping("/{playerId}")
    public void deletePlayer(@PathVariable String playerId){
        playerService.deletePlayer(playerId);
    }
}
