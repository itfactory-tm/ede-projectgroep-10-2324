package fact.it.transferservice;

import fact.it.transferservice.dto.ClubResponse;
import fact.it.transferservice.dto.PlayerResponse;
import fact.it.transferservice.dto.TransferRequest;
import fact.it.transferservice.dto.TransferResponse;
import fact.it.transferservice.model.Club;
import fact.it.transferservice.model.Player;
import fact.it.transferservice.model.Transfer;
import fact.it.transferservice.repository.TransferRepository;
import fact.it.transferservice.service.TransferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class TransferServiceApplicationTests {

    @InjectMocks
    private TransferService transferService;

    @Mock
    private TransferRepository transferRepository;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(transferService, "playerServiceBaseUrl", "localhost:8081");
        ReflectionTestUtils.setField(transferService, "clubServiceBaseUrl", "localhost:8082");
    }

    @Test
    public void testCreateTransfer() {
        // Arrange
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setPlayerNumber(1);
        transferRequest.setNewTeamName("Chelsea");
        transferRequest.setPreviousTeamName("Manchester City");

        ClubResponse newClubResponse = new ClubResponse();
        newClubResponse.setCountry("England");
        newClubResponse.setName("Chelsea");
        newClubResponse.setEstablishDate(LocalDate.of(1975, 10, 11));
        newClubResponse.setId(1L);

        ClubResponse previousClubResponse = new ClubResponse();
        previousClubResponse.setCountry("England");
        previousClubResponse.setName("Manchester City");
        previousClubResponse.setEstablishDate(LocalDate.of(1978, 8, 7));
        previousClubResponse.setId(2L);

        PlayerResponse playerResponse = new PlayerResponse();
        playerResponse.setPlayerNumber(1);
        playerResponse.setFirstName("Erling");
        playerResponse.setLastName("Haaland");
        playerResponse.setBirthDate(LocalDate.of(1998, 3, 14));
        playerResponse.setId("1");


        Transfer transfer = new Transfer();
        transfer.setTransferNumber("1234-player1234");
        transfer.setTransferDate(LocalDate.of(2024, 1, 4));

        Club newclub = new Club();
        newclub.setId(1L);
        newclub.setEstablishDate(LocalDate.of(1975, 10, 11));
        newclub.setCountry("England");
        newclub.setName("Chelsea");

        Club previousclub = new Club();
        previousclub.setId(2L);
        previousclub.setEstablishDate(LocalDate.of(1978, 8, 7));
        previousclub.setCountry("England");
        previousclub.setName("Manchester City");

        Player player = new Player();
        player.setPlayerNumber(1);
        player.setFirstName("Erling");
        player.setLastName("Haaland");
        player.setBirthDate(LocalDate.of(1998, 3, 14));
        player.setId(1L);

        transfer.setNewClub(newclub);
        transfer.setPreviousClub(previousclub);
        transfer.setPlayer(player);

        when(transferRepository.save(any(Transfer.class))).thenReturn(transfer);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(PlayerResponse.class)).thenReturn(Mono.just(playerResponse));
        when(responseSpec.bodyToMono(ClubResponse.class)).thenReturn(Mono.just(newClubResponse));
        when(responseSpec.bodyToMono(ClubResponse.class)).thenReturn(Mono.just(previousClubResponse));

        // Act
        transferService.placeTransfer(transferRequest);

        // Assert
        verify(transferRepository, times(1)).save(any(Transfer.class));
    }

    @Test
    public void testGetAllClubs() {
        //Arrange
        Transfer transfer = new Transfer();
        transfer.setId(1L);
        transfer.setTransferNumber("1234-player1234");
        transfer.setTransferDate(LocalDate.of(2024, 1, 4));

        when(transferRepository.findAll()).thenReturn(Arrays.asList(transfer));

        //Act
        List<TransferResponse> transfers = transferService.getAllTransfers();

        //Assert
        assertEquals(1, transfers.size());
        assertEquals(LocalDate.of(2024, 1, 4), transfers.get(0).getTransferDate());
        assertEquals("1234-player1234", transfers.get(0).getTransferNumber());

        verify(transferRepository, times(1)).findAll();
    }
}
