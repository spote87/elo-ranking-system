package com.elo.ranking.strategy;

import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.dao.PlayersFileDataReader;
import com.elo.ranking.model.Player;
import com.elo.ranking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote (C62183)
 */
@ExtendWith(MockitoExtension.class)
class DefaultPlayersSortingStrategyTest {

    private final PlayersDataReader playersDataReader = Mockito.mock(PlayersFileDataReader.class);

    @InjectMocks
    private DefaultPlayersSortingStrategy sortingStrategy;

    @Test
    void execute_ReturnsPlayersSortedById() {
        final List<Player> mockedData = TestUtils.mockedPlayersData().stream().sorted((a, b) -> b.getId() - a.getId()).collect(Collectors.toList());
        when(playersDataReader.readAll()).thenReturn(mockedData);
        final List<Player> players = sortingStrategy.execute();
        assertNotNull(players);
    }
}