package fact.it.playerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "player")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Player {
}
