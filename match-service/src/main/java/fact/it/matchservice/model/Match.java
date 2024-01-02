package fact.it.matchservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;

@Document(value = "match")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Match {
    @Id
    private String id;
    private LocalDate date;
    private Club homeTeam;
    private Club awayTeam;
    private int homeTeamScore;
    private int awayTeamScore;
}
