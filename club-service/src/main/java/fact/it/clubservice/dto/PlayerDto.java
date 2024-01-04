package fact.it.clubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
    private int playerNumber;
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
}
