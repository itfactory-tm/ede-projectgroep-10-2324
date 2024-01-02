package fact.it.matchservice.dto;

import fact.it.matchservice.model.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchResponse {
    private String id;
    private LocalDate date;
    private Club homeTeam;
    private Club awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
}
