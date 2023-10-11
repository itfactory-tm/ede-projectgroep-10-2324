package fact.it.matchservice.controller;

import fact.it.matchservice.dto.MatchRequest;
import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.service.MatchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match")
@RequiredArgsConstructor
public class MatchController {

    private final MatchService matchService;

    @PostMapping
    public void createMatch
            (@RequestBody MatchRequest matchRequest) {
        matchService.createMatch(matchRequest);
    }

    @GetMapping("/all")
    public List<MatchResponse> getAllMatches() {
        return matchService.getAllMatches();
    }

    @DeleteMapping("/{matchId}")
    public void deleteMatch(@PathVariable String matchId){
        matchService.deleteMatch(matchId);
    }
}
