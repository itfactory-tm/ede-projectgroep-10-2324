package fact.it.clubservice.controller;

import fact.it.clubservice.dto.ClubRequest;
import fact.it.clubservice.dto.ClubResponse;
import fact.it.clubservice.model.Club;
import fact.it.clubservice.service.ClubService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/club")
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String createClub(@RequestBody ClubRequest clubRequest) {
        clubService.createClub(clubRequest);
        return "Club created successfully";
    }

    // http://localhost:8082/api/club?name=FC%20Barcelona&name=Real%20Madrid%20CF
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ClubResponse> getAllClubs() {
        return clubService.getAllClubs();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ClubResponse getClub(@RequestParam String clubName) {
        return clubService.getClub(clubName);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateClub(@PathVariable("id") Long id, @RequestBody ClubRequest clubRequest) {
        boolean updated = clubService.updateClub(id, clubRequest);

        if (updated) {
            return ResponseEntity.ok("Club updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Club not found");
        }
    }
}
