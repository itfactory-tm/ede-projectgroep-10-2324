package fact.it.clubservice.controller;

import fact.it.clubservice.dto.ClubResponse;
import fact.it.clubservice.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    // http://localhost:8082/api/club?name=FC%20Barcelona&name=Real%20Madrid%20CF
    @GetMapping("/all")
    public List<ClubResponse> getAllClubs() {
        return clubService.getAllClubs();
    }

}
