package fact.it.clubservice.service;

import fact.it.clubservice.dto.ClubRequest;
import fact.it.clubservice.dto.ClubResponse;
import fact.it.clubservice.dto.PlayerDto;
import fact.it.clubservice.dto.PlayerResponse;
import fact.it.clubservice.model.Club;
import fact.it.clubservice.model.Player;
import fact.it.clubservice.repository.ClubRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final ClubRepository clubRepository;
    private final WebClient webClient;
    @Value("${playerservice.baseurl}")
    private String playerServiceBaseUrl;

    @PostConstruct
    public void loadData() {
        if(clubRepository.count() <= 0) {
            Club club1 = new Club();
            club1.setName("FC Barcelona");
            club1.setCountry("Spain");
            club1.setEstablishDate(LocalDate.of(1967, 7, 28));

            Club club2 = new Club();
            club2.setName("Real Madrid CF");
            club2.setCountry("Spain");
            club2.setEstablishDate(LocalDate.of(1963, 2, 14));

            clubRepository.save(club1);
            clubRepository.save(club2);
        }
    }

    public void createClub(ClubRequest clubRequest){
        Club club = new Club();
        club.setName(clubRequest.getName());
        club.setCountry(clubRequest.getCountry());
        club.setEstablishDate(LocalDate.now());

        List<Integer> playerNumbers = clubRequest.getPlayerNumbers().stream()
                .toList();

        List<PlayerResponse> players = webClient.get()
                .uri("http://" + playerServiceBaseUrl + "/api/player",
                        uriBuilder -> uriBuilder.queryParam("playerNumber", playerNumbers).build())
                .retrieve()
                .bodyToFlux(PlayerResponse.class)
                .collectList()
                .block();

        assert players != null;
        List<Player> playerList = players.stream()
                .map(this::mapPlayerResponseToEntity)
                .toList();

        club.setPlayerList(playerList);
        clubRepository.save(club);
    }

    private Player mapPlayerResponseToEntity(PlayerResponse playerResponse) {
        Player player = new Player();
        player.setPlayerNumber(playerResponse.getPlayerNumber());
        player.setFirstName(playerResponse.getFirstName());
        player.setLastName(playerResponse.getLastName());
        player.setBirthDate(playerResponse.getBirthDate());
        return player;
    }

    @Transactional
    public boolean updateClub(Long id, ClubRequest clubRequest) {
        Optional<Club> optionalClub = clubRepository.findById(id);

        if (optionalClub.isPresent()) {
            Club existingClub = optionalClub.get();
            existingClub.setName(clubRequest.getName());
            existingClub.setCountry(clubRequest.getCountry());

            clubRepository.save(existingClub);
            return true;
        } else {
            return false;
        }
    }

    private List<Player> mapPlayerDtoList(List<PlayerDto> playerDtoList) {
        return playerDtoList.stream()
                .map(this::mapToDto)
                .toList();
    }

    private Player mapToDto(PlayerDto playerDto) {
        Player player = new Player();
        player.setBirthDate(playerDto.getBirthDate());
        player.setFirstName(playerDto.getFirstName());
        player.setLastName(playerDto.getLastName());
        return player;
    }

    @Transactional
    public List<ClubResponse> getAllClubs() {
        List<Club> clubs = clubRepository.findAll();

        return clubs.stream().map(this::mapToClubResponse).toList();
    }

    private ClubResponse mapToClubResponse(Club club) {
        List<PlayerDto> playerDtoList = club.getPlayerList().stream()
                .map(this::mapPlayerToDto)
                .toList();

        return ClubResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .country(club.getCountry())
                .establishDate(club.getEstablishDate())
                .playerDtoList(playerDtoList)
                .build();
    }

    private PlayerDto mapPlayerToDto(Player player) {
        return new PlayerDto(player.getPlayerNumber(), player.getFirstName(), player.getLastName(), player.getBirthDate());
    }

    @Transactional
    public ClubResponse getClub(String clubName) {
        Club club = clubRepository.findByName(clubName);

        if (club != null) {
            return mapToClubMatchResponse(club);
        } else {
            return new ClubResponse();
        }
    }

    private ClubResponse mapToClubMatchResponse(Club club) {
        return ClubResponse.builder()
                .id(club.getId())
                .name(club.getName())
                .country(club.getCountry())
                .establishDate(club.getEstablishDate())
                .build();
    }
}
