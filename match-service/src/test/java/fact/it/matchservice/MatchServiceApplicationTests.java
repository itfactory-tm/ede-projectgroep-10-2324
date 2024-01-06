package fact.it.matchservice;

import fact.it.matchservice.dto.ClubResponse;
import fact.it.matchservice.dto.MatchRequest;
import fact.it.matchservice.dto.MatchResponse;
import fact.it.matchservice.model.Club;
import fact.it.matchservice.model.Match;
import fact.it.matchservice.repository.MatchRepository;
import fact.it.matchservice.service.MatchService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class MatchServiceApplicationTests {

	@InjectMocks
	private MatchService matchService;
	@Mock
	private MatchRepository matchRepository;
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
		ReflectionTestUtils.setField(matchService, "clubServiceBaseUrl", "localhost:8082");
	}
	@Test
	public void testCreateMatch() {
		// Arrange
		MatchRequest matchRequest = new MatchRequest();
		matchRequest.setHomeTeamName("Manchester City");
		matchRequest.setHomeTeamScore(3);
		matchRequest.setAwayTeamName("FC Barcelona");
		matchRequest.setAwayTeamScore(1);
		matchRequest.setDate(LocalDate.of(2024, 1, 4));

		ClubResponse homeClubResponse = new ClubResponse();
		homeClubResponse.setCountry("England");
		homeClubResponse.setName("Manchester City");
		homeClubResponse.setEstablishDate(LocalDate.of(1998, 3, 14)); // Set the desired date
		homeClubResponse.setId(1L);

		ClubResponse awayClubResponse = new ClubResponse();
		awayClubResponse.setCountry("Spain");
		awayClubResponse.setName("FC Barcelona");
		awayClubResponse.setEstablishDate(LocalDate.of(1967, 7, 28)); // Set the desired date
		awayClubResponse.setId(2L);


		Match match = new Match();
		match.setId("1");
		match.setHomeTeamScore(3);
		match.setAwayTeamScore(1);
		match.setDate(LocalDate.of(2024, 1, 4));

		Club homeClub = new Club();
		homeClub.setCountry("England");
		homeClub.setName("Manchester City");
		homeClub.setEstablishDate(LocalDate.of(1998, 3, 14));
		homeClub.setId(1L);

		Club awayClub = new Club();
		awayClub.setCountry("Spain");
		awayClub.setName("FC Barcelona");
		awayClub.setEstablishDate(LocalDate.of(1967, 7, 28));
		awayClub.setId(2L);

		match.setHomeTeam(homeClub);
		match.setAwayTeam(awayClub);

		when(matchRepository.save(any(Match.class))).thenReturn(match);

		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToMono(ClubResponse.class)).thenReturn(Mono.just(homeClubResponse));
		when(responseSpec.bodyToMono(ClubResponse.class)).thenReturn(Mono.just(awayClubResponse));

		// Act
		matchService.createMatch(matchRequest);

		// Assert
		verify(matchRepository, times(1)).save(any(Match.class));
	}

	@Test
	public void testGetAllMatches(){
		// Arrange
		Club homeClub = new Club();
		homeClub.setId(1L);
		homeClub.setEstablishDate(LocalDate.of(1975, 10, 11));
		homeClub.setCountry("England");
		homeClub.setName("Manchester City");

		Club awayClub = new Club();
		awayClub.setId(2L);
		awayClub.setEstablishDate(LocalDate.of(1975, 10, 11));
		awayClub.setCountry("Spain");
		awayClub.setName("Barcelona");

		Match match = new Match();
		match.setId("1");
		match.setDate(LocalDate.of(2024, 1, 4));
		match.setHomeTeam(homeClub);
		match.setAwayTeam(awayClub);
		match.setHomeTeamScore(2);
		match.setAwayTeamScore(1);

		when(matchRepository.findAll()).thenReturn(Arrays.asList(match));

		// Act
		List<MatchResponse> matches = matchService.getAllMatches();

		// Assert
		assertEquals(1, matches.size());
		assertEquals(LocalDate.of(2024, 1, 4), matches.get(0).getDate());
		assertEquals(2, matches.get(0).getHomeTeamScore());
		assertEquals(1, matches.get(0).getAwayTeamScore());
		assertEquals(homeClub, matches.get(0).getHomeTeam());
		assertEquals(awayClub, matches.get(0).getAwayTeam());

		verify(matchRepository, times(1)).findAll();
	}
	@Test
	public void testDeleteMatch() {
		// Arrange
		Club homeClub = new Club();
		homeClub.setId(1L);
		homeClub.setEstablishDate(LocalDate.of(1975, 10, 11));
		homeClub.setCountry("England");
		homeClub.setName("Manchester City");

		Club awayClub = new Club();
		awayClub.setId(2L);
		awayClub.setEstablishDate(LocalDate.of(1975, 10, 11));
		awayClub.setCountry("Spain");
		awayClub.setName("Barcelona");

		Match match = new Match();
		match.setId("1");
		match.setDate(LocalDate.of(2024, 1, 4));
		match.setHomeTeam(homeClub);
		match.setAwayTeam(awayClub);
		match.setHomeTeamScore(2);
		match.setAwayTeamScore(1);

		when(matchRepository.existsById("1")).thenReturn(true);

		// Act
		matchService.deleteMatch("1");

		// Assert
		verify(matchRepository, times(1)).existsById("1");
		verify(matchRepository, times(1)).deleteById("1");
	}
}
