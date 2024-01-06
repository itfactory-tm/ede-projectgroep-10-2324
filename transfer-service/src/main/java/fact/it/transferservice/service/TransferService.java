package fact.it.transferservice.service;

import fact.it.transferservice.dto.ClubResponse;
import fact.it.transferservice.dto.PlayerResponse;
import fact.it.transferservice.dto.TransferRequest;
import fact.it.transferservice.dto.TransferResponse;
import fact.it.transferservice.model.Club;
import fact.it.transferservice.model.Player;
import fact.it.transferservice.model.Transfer;
import fact.it.transferservice.repository.TransferRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransferService {
    private final TransferRepository transferRepository;
    private final WebClient webClient;
    @PersistenceContext
    private EntityManager entityManager;

    @Value("${playerservice.baseurl}")
    private String playerServiceBaseUrl;

    @Value("${clubservice.baseurl}")
    private String clubServiceBaseUrl;

    @Transactional
    public void placeTransfer(TransferRequest transferRequest) {
        Transfer transfer = new Transfer();
        transfer.setTransferNumber(UUID.randomUUID().toString());
        transfer.setTransferDate(LocalDate.now());

        PlayerResponse player = webClient.get()
                .uri("http://" + playerServiceBaseUrl + "/api/player/",
                        uriBuilder -> uriBuilder.queryParam("playerNumber", transferRequest.getPlayerNumber()).build())
                .retrieve()
                .bodyToMono(PlayerResponse.class)
                .block();

        ClubResponse previousClub = webClient.get()
                .uri("http://" + clubServiceBaseUrl + "/api/club",
                        uriBuilder -> uriBuilder.queryParam("clubName", transferRequest.getPreviousTeamName()).build())
                .retrieve()
                .bodyToMono(ClubResponse.class)
                .block();

        ClubResponse newClub = webClient.get()
                .uri("http://" + clubServiceBaseUrl + "/api/club",
                        uriBuilder -> uriBuilder.queryParam("clubName", transferRequest.getNewTeamName()).build())
                .retrieve()
                .bodyToMono(ClubResponse.class)
                .block();

//        player = entityManager.merge(player);
//        previousClub = entityManager.merge(previousClub);
//        newClub = entityManager.merge(newClub);

        assert player != null;
        assert previousClub != null;
        assert newClub != null;

        transfer.setPlayer(mapPlayerResponseToEntity(player));
        transfer.setPreviousClub(mapClubResponseToEntity(previousClub));
        transfer.setNewClub(mapClubResponseToEntity(newClub));

        transferRepository.save(transfer);
    }

    private Player mapPlayerResponseToEntity(PlayerResponse playerResponse) {
        Player player = new Player();
        player.setPlayerNumber(playerResponse.getPlayerNumber());
        player.setFirstName(playerResponse.getFirstName());
        player.setLastName(playerResponse.getLastName());
        player.setBirthDate(playerResponse.getBirthDate());
        return player;
    }

    private Club mapClubResponseToEntity(ClubResponse clubResponse) {
        Club club = new Club();
        club.setName(clubResponse.getName());
        club.setCountry(clubResponse.getCountry());
        club.setEstablishDate(clubResponse.getEstablishDate());
        return club;
    }

    public List<TransferResponse> getAllTransfers() {
        List<Transfer> transfers = transferRepository.findAll();

        return transfers.stream()
                .map(transfer -> new TransferResponse(
                        transfer.getTransferNumber(),
                        transfer.getTransferDate(),
                        transfer.getPlayer(),
                        transfer.getNewClub(),
                        transfer.getPreviousClub()
                ))
                .collect(Collectors.toList());
    }
}
