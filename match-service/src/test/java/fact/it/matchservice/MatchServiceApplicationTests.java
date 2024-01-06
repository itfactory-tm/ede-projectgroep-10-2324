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
		// arrange
		MatchRequest matchRequest = new MatchRequest();
		matchRequest.setHomeTeamName("Gangsters");
		matchRequest.setHomeTeamScore(100);
		matchRequest.setAwayTeamName("Losers");
		matchRequest.setAwayTeamScore(0);
		LocalDate date = LocalDate.now();
		matchRequest.setDate(date);

		ClubResponse clubResponse = new ClubResponse();
		clubResponse.setCountry("England");
		clubResponse.setName("Manchester City");
		clubResponse.setEstablishDate(LocalDate.of(2023, 2, 6)); // Set the desired date
		clubResponse.setId(1L);

		Club club = new Club();
		club.setId(1L);
		club.setEstablishDate(LocalDate.of(1975, 10, 11));
		club.setCountry("England");
		club.setName("Manchester City");

		Club club2 = new Club();
		club2.setId(2L);
		club2.setEstablishDate(LocalDate.of(1975, 10, 11));
		club2.setCountry("Spain");
		club2.setName("Barcelona");

		Match match = new Match();
		match.setId("1");
		match.setHomeTeam(club);
		match.setHomeTeamScore(100);
		match.setAwayTeam(club2);
		match.setAwayTeamScore(0);
		match.setDate(date);

		when(matchRepository.save(any(Match.class))).thenReturn(match);

		when(webClient.get()).thenReturn(requestHeadersUriSpec);
		when(requestHeadersUriSpec.uri(anyString(), any(Function.class))).thenReturn(requestHeadersSpec);
		when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
		when(responseSpec.bodyToFlux(ClubResponse.class)).thenReturn(Flux.just(clubResponse));

		// act
		matchService.createMatch(matchRequest);

		// assert
		verify(matchRepository, times(0)).save(match);
	}

	@Test
	public void testGetAllMatches(){
		// arrange
		Club club = new Club();
		club.setId(1L);
		club.setEstablishDate(LocalDate.of(1975, 10, 11));
		club.setCountry("England");
		club.setName("Manchester City");

		Club club2 = new Club();
		club2.setId(2L);
		club2.setEstablishDate(LocalDate.of(1975, 10, 11));
		club2.setCountry("Spain");
		club2.setName("Barcelona");

		Match match = new Match();
		match.setId("1");
		match.setDate(LocalDate.of(2003, 12, 24));
		match.setHomeTeam(club);
		match.setAwayTeam(club2);
		match.setHomeTeamScore(2);
		match.setAwayTeamScore(1);

		when(matchRepository.findAll()).thenReturn(Arrays.asList(match));

		// act
		List<MatchResponse> matches = matchService.getAllMatches();

		// assert
		assertEquals(1, matches.size());
		assertEquals(LocalDate.of(2003, 12, 24), matches.get(0).getDate());
		assertEquals(2, matches.get(0).getHomeTeamScore());
		assertEquals(1, matches.get(0).getAwayTeamScore());
		assertEquals(club, matches.get(0).getHomeTeam());
		assertEquals(club2, matches.get(0).getAwayTeam());

		verify(matchRepository, times(1)).findAll();
	}
	@Test
	public void testGetMatch() {
		// arrange
		Club club = new Club();
		club.setId(1L);
		club.setEstablishDate(LocalDate.of(1975, 10, 11));
		club.setCountry("England");
		club.setName("Manchester City");

		Club club2 = new Club();
		club2.setId(2L);
		club2.setEstablishDate(LocalDate.of(1975, 10, 11));
		club2.setCountry("Spain");
		club2.setName("Barcelona");

		Match match = new Match();
		match.setId("1");
		match.setDate(LocalDate.of(2003, 12, 24));
		match.setHomeTeam(club);
		match.setAwayTeam(club2);
		match.setHomeTeamScore(2);
		match.setAwayTeamScore(1);


		when(matchRepository.findByDate(LocalDate.of(2003, 12, 24)).thenReturn(match);

		// act

		// assert

	}
}
