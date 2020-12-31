package com.elo.ranking.dao;

import com.elo.ranking.model.Player;
import com.elo.ranking.reader.EloFileReader;
import com.elo.ranking.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote
 */
@ExtendWith(MockitoExtension.class)
public class PlayersFileDataReaderTest {

    @InjectMocks
    private PlayersFileDataReader dataReader;

    //@Mock
    private final EloFileReader fileReader = Mockito.mock(EloFileReader.class);

    @BeforeEach
    public void setup() {
        //MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(dataReader, "dataFile", "players/players.txt");
        ReflectionTestUtils.setField(dataReader, "separator", "    ");
    }

    @Test
    public void readAll_ReadsOnlyValidPlayersDataFromFile() throws IOException {
        when(fileReader.readResourceAsInputStream(anyString())).thenReturn(TestUtils.readResourceAsStream("players/players.txt"));
        final List<Player> players = dataReader.readAll();
        assertNotNull(players);
        assertEquals(4, players.size());
    }

    @Test
    public void byName_ReturnsPlayerByItsName() throws IOException {
        when(fileReader.readResourceAsInputStream(anyString())).thenReturn(TestUtils.readResourceAsStream("players/players.txt"));
        final Player player = dataReader.byName("Melodie");
        assertNotNull(player);
        assertEquals(1, player.getId());
        assertEquals("Melodie", player.getName());
    }

    @Test
    public void byName_ReturnsNullWhenPlayerDoesNotExistInSystem() throws IOException {
        when(fileReader.readResourceAsInputStream(anyString())).thenReturn(TestUtils.readResourceAsStream("players/players.txt"));
        final Player player = dataReader.byName("NotPresent");
        assertNull(player);
    }

    @Test
    public void byId_ReturnsPlayerByItsId() throws IOException {
        when(fileReader.readResourceAsInputStream(anyString())).thenReturn(TestUtils.readResourceAsStream("players/players.txt"));
        final Player player = dataReader.byId(1);
        assertNotNull(player);
        assertEquals(1, player.getId());
        assertEquals("Melodie", player.getName());
    }

    @Test
    public void byId_ReturnsNullWhenPlayerDoesNotExistInSystem() throws IOException {
        when(fileReader.readResourceAsInputStream(anyString())).thenReturn(TestUtils.readResourceAsStream("players/players.txt"));
        final Player player = dataReader.byId(99999);
        assertNull(player);
    }

    @Test
    public void readAll_ReturnsEmptyListWhenReaderFailsToReadPlayersResource() throws IOException {
        when(fileReader.readResourceAsInputStream(anyString())).thenThrow(IOException.class);
        final List<Player> players = dataReader.readAll();
        assertNotNull(players);
        assertTrue(players.isEmpty());
    }
}
