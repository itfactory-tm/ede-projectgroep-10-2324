package fact.it.transferservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerResponse {
    private int playerNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
