package fact.it.clubservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClubRequest {
    private String name;
    private String country;
    //private List<PlayerDto> playerDtoList;
    private List<Integer> playerNumbers;
}
