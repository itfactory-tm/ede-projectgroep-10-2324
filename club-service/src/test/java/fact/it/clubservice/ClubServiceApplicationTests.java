package fact.it.clubservice;

import fact.it.clubservice.dto.ClubRequest;
import fact.it.clubservice.dto.ClubResponse;
import fact.it.clubservice.dto.PlayerResponse;
import fact.it.clubservice.model.Club;
import fact.it.clubservice.model.Player;
import fact.it.clubservice.repository.ClubRepository;
import fact.it.clubservice.service.ClubService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cglib.core.Local;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ClubServiceApplicationTests {

	@InjectMocks
	private ClubService clubService;

	@Mock
	private ClubRepository clubRepository;

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
		ReflectionTestUtils.setField(clubService, "playerServiceBaseUrl", "localhost:8081");
	}
	@Test
	public void testCreateClub() {
		// Arrange
		ClubRequest clubRequest = new ClubRequest();
		clubRequest.setCountry("England");
		clubRequest.setName("Manchester City");
		clubRequest.setPlayerNumbers(Arrays.asList(1));

		PlayerResponse playerResponse = new PlayerResponse();
		playerResponse.setPlayerNumber(1);
		playerResponse.setFirstName("Erling");
		playerResponse.setLastName("Haaland");
		playerResponse.setBirthDate(LocalDate.of(1998, 3, 14));
		playerResponse.setId("1");

		Club club = new Club();
		club.setId(1L);
		club.setEstablishDate(LocalDate.of(1975, 10, 11));
		club.setCountry("England");
		club.setName("Manchester City");
		Player player = new Player();
		player.setPlayerNumber(1);
		player.setFirstName("Erling");
		player.setLastName("Haaland");
		player.setBirthDate(LocalDate.of(1998, 3, 14));
		player.setId(1L);
		club.setPlayerList(Arrays.asList(player));

		when(clubRepository.save(any(Club.class))).thenReturn(club);

		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToFlux(PlayerResponse.class)).thenReturn(Flux.just(playerResponse));

		// Act
		clubService.createClub(clubRequest);

		// Assert
		verify(clubRepository, times(1)).save(any(Club.class));
	}

	@Test
	public void testGetAllClubs() {
		//Arrange
		Club club = new Club();
		club.setId(1L);
		club.setEstablishDate(LocalDate.of(1975, 10, 11));
		club.setName("Manchester City");
		club.setCountry("England");

		when(clubRepository.findAll()).thenReturn(Arrays.asList(club));

		//Act
		List<ClubResponse> clubs = clubService.getAllClubs();

		//Assert
		assertEquals(1, clubs.size());
		assertEquals(LocalDate.of(1975, 10, 11), clubs.get(0).getEstablishDate());
		assertEquals("Manchester City", clubs.get(0).getName());
		assertEquals("England", clubs.get(0).getCountry());

		verify(clubRepository, times(1)).findAll();
	}

	@Test
	public void testgetClub() {
		//Arrange
		Club club = new Club();
		club.setId(1L);
		club.setEstablishDate(LocalDate.of(1975, 10, 11));
		club.setName("Manchester City");
		club.setCountry("England");

		when(clubRepository.findByName("Manchester City")).thenReturn(club);

		//Act
		ClubResponse foundClub = clubService.getClub("Manchester City");

		//Assert
		assertEquals(LocalDate.of(1975, 10, 11), foundClub.getEstablishDate());
		assertEquals("Manchester City", foundClub.getName());
		assertEquals("England", foundClub.getCountry());

		verify(clubRepository, times(1)).findByName(foundClub.getName());
	}

	@Test
	public void testUpdateClub() {
		// Arrange
		Long clubId = 1L;
		ClubRequest clubRequest = new ClubRequest();
		clubRequest.setName("FC Barcelona");
		clubRequest.setCountry("Spain");

		Club existingClub = new Club();
		existingClub.setId(clubId);
		existingClub.setEstablishDate(LocalDate.of(1967, 7, 28));
		existingClub.setName("Real Madrid CF");
		existingClub.setCountry("Spain");

		when(clubRepository.findById(clubId)).thenReturn(Optional.of(existingClub));
		when(clubRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0)); // Mock save to return the saved entity

		// Act
		boolean updated = clubService.updateClub(clubId, clubRequest);

		// Assert
		assertTrue(updated);

		assertEquals("FC Barcelona", existingClub.getName());
		assertEquals("Spain", existingClub.getCountry());

		verify(clubRepository, times(1)).findById(clubId);
		verify(clubRepository, times(1)).save(existingClub);
	}
}
