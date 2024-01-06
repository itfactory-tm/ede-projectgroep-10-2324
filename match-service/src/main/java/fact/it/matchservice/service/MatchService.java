package fact.it.matchservice.service;

import fact.it.matchservice.dto.ClubResponse;
import fact.it.matchservice.dto.MatchRequest;
import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.model.Club;
import fact.it.matchservice.model.Match;
import fact.it.matchservice.repository.MatchRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    private final MatchRepository matchRepository;
    private final WebClient webClient;

    @Value("${clubservice.baseurl}")
    private String clubServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        if(matchRepository.count() <= 0){
            Club club1 = new Club();
            club1.setName("FC Barcelona");
            club1.setCountry("Spain");
            club1.setEstablishDate(LocalDate.of(1984, 2, 12));

            Club club2 = new Club();
            club2.setName("Real Madrid CF");
            club2.setCountry("Spain");
            club2.setEstablishDate(LocalDate.of(1978, 4, 24));

            Club club3 = new Club();
            club3.setName("Liverpool");
            club3.setCountry("England");
            club3.setEstablishDate(LocalDate.of(1968, 11, 27));

            Match match = Match.builder()
                    .date(LocalDate.of(2022, 12, 18))
                    .homeTeam(club1)
                    .awayTeam(club2)
                    .homeTeamScore(3)
                    .awayTeamScore(1)
                    .build();

            Match match1 = Match.builder()
                    .date(LocalDate.of(2022, 12, 14))
                    .homeTeam(club2)
                    .awayTeam(club3)
                    .homeTeamScore(2)
                    .awayTeamScore(3)
                    .build();

            matchRepository.save(match);
            matchRepository.save(match1);
        }
    }

    public void createMatch(MatchRequest matchRequest){
        ClubResponse club1 = webClient.get()
                .uri("http://" + clubServiceBaseUrl + "/api/club",
                        uriBuilder -> uriBuilder.queryParam("clubName", matchRequest.getHomeTeamName()).build())
                .retrieve()
                .bodyToMono(ClubResponse.class)
                .block();

        ClubResponse club2 = webClient.get()
                .uri("http://" + clubServiceBaseUrl + "/api/club",
                        uriBuilder -> uriBuilder.queryParam("clubName", matchRequest.getAwayTeamName()).build())
                .retrieve()
                .bodyToMono(ClubResponse.class)
                .block();

        assert club1 != null;
        assert club2 != null;

        Match match = Match.builder()
                .date(matchRequest.getDate())
                .homeTeam(mapClubResponseToEntity(club1))
                .awayTeam(mapClubResponseToEntity(club2))
                .homeTeamScore(matchRequest.getHomeTeamScore())
                .awayTeamScore(matchRequest.getAwayTeamScore())
                .build();
        matchRepository.save(match);
    }

    private Club mapClubResponseToEntity(ClubResponse clubResponse) {
        Club club = new Club();
        club.setId(clubResponse.getId());
        club.setName(clubResponse.getName());
        club.setCountry(clubResponse.getCountry());
        club.setEstablishDate(clubResponse.getEstablishDate());
        return club;
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
                .homeTeam(match.getHomeTeam())
                .awayTeam(match.getAwayTeam())
                .homeTeamScore(match.getHomeTeamScore())
                .awayTeamScore(match.getAwayTeamScore())
                .build();
    }
}
