package fact.it.clubservice.service;

import fact.it.clubservice.dto.ClubResponse;
import fact.it.clubservice.model.Club;
import fact.it.clubservice.repository.ClubRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;

    @PostConstruct
    public void loadData() {
        if(clubRepository.count() <= 0) {
            Club club1 = new Club();
            club1.setName("FC Barcelona");

            Club club2 = new Club();
            club2.setName("Real Madrid CF");

            clubRepository.save(club1);
            clubRepository.save(club2);
        }
    }

    @Transactional
    public List<ClubResponse> getAllClubs() {
        List<Club> clubs = clubRepository.findAll();

        return clubs.stream().map(this::mapToClubResponse).toList();
    }

    private ClubResponse mapToClubResponse(Club club) {
        return ClubResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .build();
    }
}
