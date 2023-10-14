package fact.it.transferservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "transfers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String transferNumber;
    private LocalDate transferDate;
    @ManyToOne(cascade = CascadeType.ALL)
    private Club previousClub;
    @ManyToOne(cascade = CascadeType.ALL)
    private Club newClub;
}
