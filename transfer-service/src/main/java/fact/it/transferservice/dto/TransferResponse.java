package fact.it.transferservice.dto;

import fact.it.transferservice.model.Club;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferResponse {
    private String transferNumber;
    private LocalDate transferDate;
    private Club  newClub;
    private Club previousClub;
}

