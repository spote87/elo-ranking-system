package com.elo.ranking.service;

import com.elo.ranking.dao.MatchesDataReader;
import com.elo.ranking.dao.MatchesFileDataReader;
import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.dao.PlayersFileDataReader;
import com.elo.ranking.model.Match;
import com.elo.ranking.model.Player;
import com.elo.ranking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote
 */
@ExtendWith(MockitoExtension.class)
public class ScoreServiceImplTest {

    private final MatchesDataReader matchesDataReader = Mockito.mock(MatchesFileDataReader.class);

    private final PlayersDataReader playersDataReader = Mockito.mock(PlayersFileDataReader.class);

    @InjectMocks
    private ScoreServiceImpl scoreService;

    @Test
    public void getScore_ReturnsScoreOfIndividualPlayer() {
        final List<Match> mockedMatches = TestUtils.mockedMatchesData();
        when(matchesDataReader.read()).thenReturn(mockedMatches);
        final int score = scoreService.getScore(2);
        assertEquals(3, score);
    }

    @Test
    public void getScore_Returns0IfPlayerDoesNotExistInList() {
        final List<Match> mockedMatches = TestUtils.mockedMatchesData();
        when(matchesDataReader.read()).thenReturn(mockedMatches);
        final int score = scoreService.getScore(22);
        assertEquals(0, score);
    }

    @Test
    public void getScores_ReturnsMapOfPlayerIdAndScoresOfAllUsers() {
        final List<Match> mockedMatches = TestUtils.mockedMatchesData();
        final List<Player> mockedPlayers = TestUtils.mockedPlayersData();
        when(matchesDataReader.read()).thenReturn(mockedMatches);
        when(playersDataReader.readAll()).thenReturn(mockedPlayers);
        final Map<Integer, Integer> playerScores = scoreService.getPlayerScores();
        assertNotNull(playerScores);
        assertEquals(0, playerScores.get(3));
        assertEquals(3, playerScores.get(2));
    }

}
