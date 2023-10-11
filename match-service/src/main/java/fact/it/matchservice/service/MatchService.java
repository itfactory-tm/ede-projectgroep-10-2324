package fact.it.matchservice.service;

import fact.it.matchservice.dto.MatchRequest;
import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.model.Match;
import fact.it.matchservice.repository.MatchRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;

    @PostConstruct
    public void loadData() {
        if(matchRepository.count() <= 0){
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date, date1;
            try {
                date = dateFormat.parse("18/12/2022");
                date1 = dateFormat.parse("14/12/2022");
            } catch (ParseException e) {
                // Handle parsing exception as needed
                date = null;
                date1 = null;
            }
            Match match = Match.builder()
                    .date(date)
                    .build();

            Match match1 = Match.builder()
                    .date(date1)
                    .build();

            matchRepository.save(match);
            matchRepository.save(match1);
        }
    }

    public void createMatch(MatchRequest matchRequest){
        Match match = Match.builder()
                .date(matchRequest.getDate())
                .build();

        matchRepository.save(match);
    }

    public List<MatchResponse> getAllMatches() {
        List<Match> matches = matchRepository.findAll();

        return matches.stream().map(this::mapToMatchResponse).toList();
    }

    public void deleteMatch(String matchId){
        if(matchRepository.existsById(matchId)){
            matchRepository.deleteById(matchId);
        }
    }

    private MatchResponse mapToMatchResponse(Match match) {
        return MatchResponse.builder()
                .id(match.getId())
                .date(match.getDate())
                .build();
    }
}
