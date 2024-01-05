package fact.it.playerservice;

import fact.it.playerservice.dto.PlayerRequest;
import fact.it.playerservice.dto.PlayerResponse;
import fact.it.playerservice.model.Player;
import fact.it.playerservice.repository.PlayerRepository;
import fact.it.playerservice.service.PlayerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PlayerServiceApplicationTests {

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Test
    public void testCreatePlayer() {
        //Arrange
        PlayerRequest playerRequest = new PlayerRequest();
        playerRequest.setFirstName("Cristiano");
        playerRequest.setLastName("Ronaldo");
        playerRequest.setBirthDate(LocalDate.of(1985, 2, 5));

        //Act
        playerService.createPlayer(playerRequest);

        //Assert
        verify(playerRepository, times(1)).save(any(Player.class));
    }

    @Test
    public void testGetAllPlayers() {
        //Arrange
        Player player = new Player();
        player.setId("1");
        player.setPlayerNumber(4);
        player.setBirthDate(LocalDate.of(2001, 3, 14));
        player.setLastName("Simons");
        player.setFirstName("Xavi");

        when(playerRepository.findAll()).thenReturn(Arrays.asList(player));

        //Act
        List<PlayerResponse> players = playerService.getAllPlayers();

        //Assert
        assertEquals(1, players.size());
        assertEquals(4, players.get(0).getPlayerNumber());
        assertEquals(LocalDate.of(2001, 3, 14), players.get(0).getBirthDate());
        assertEquals("Simons", players.get(0).getLastName());
        assertEquals("Xavi", players.get(0).getFirstName());

        verify(playerRepository, times(1)).findAll();
    }

    @Test
    public void testGetPlayers() {
        //Arrange
        Player player = new Player();
        player.setId("1");
        player.setPlayerNumber(4);
        player.setBirthDate(LocalDate.of(2001, 3, 14));
        player.setLastName("Simons");
        player.setFirstName("Xavi");

        when(playerRepository.findByPlayerNumberIn(Arrays.asList(4))).thenReturn(Arrays.asList(player));

        //Act
        List<PlayerResponse> players = playerService.getPlayers(Arrays.asList(4));

        //Assert
        assertEquals(1, players.size());
        assertEquals(4, players.get(0).getPlayerNumber());
        assertEquals(LocalDate.of(2001, 3, 14), players.get(0).getBirthDate());
        assertEquals("Simons", players.get(0).getLastName());
        assertEquals("Xavi", players.get(0).getFirstName());

        verify(playerRepository, times(1)).findByPlayerNumberIn(Arrays.asList(player.getPlayerNumber()));
    }

    @Test
    public void testgetPlayer() {
        //Arrange
        Player player = new Player();
        player.setId("1");
        player.setPlayerNumber(4);
        player.setBirthDate(LocalDate.of(2001, 3, 14));
        player.setLastName("Simons");
        player.setFirstName("Xavi");

        when(playerRepository.findPlayerByPlayerNumber(4)).thenReturn(player);

        //Act
        PlayerResponse foundPlayer = playerService.getPlayer(4);

        //Assert
        assertEquals(4, foundPlayer.getPlayerNumber());
        assertEquals(LocalDate.of(2001, 3, 14), foundPlayer.getBirthDate());
        assertEquals("Simons", foundPlayer.getLastName());
        assertEquals("Xavi", foundPlayer.getFirstName());


        verify(playerRepository, times(1)).findPlayerByPlayerNumber(foundPlayer.getPlayerNumber());
    }

    @Test
    public void testDeletePlayer() {
        // Arrange
        Player player = new Player();
        player.setId("1");
        player.setPlayerNumber(4);
        player.setBirthDate(LocalDate.of(2001, 3, 14));
        player.setLastName("Simons");
        player.setFirstName("Xavi");

        when(playerRepository.existsById("1")).thenReturn(true);

        // Act
        playerService.deletePlayer("1");

        // Assert
        verify(playerRepository, times(1)).existsById("1");
        verify(playerRepository, times(1)).deleteById("1");
    }
}
