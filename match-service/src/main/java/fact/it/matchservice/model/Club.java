package fact.it.matchservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(value = "club")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Club {
    @Id
    private Long id;
    private String name;
    private String country;
    private LocalDate establishDate;
}
