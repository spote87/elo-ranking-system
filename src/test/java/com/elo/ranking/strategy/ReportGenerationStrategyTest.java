package com.elo.ranking.strategy;

import com.elo.ranking.dao.MatchesDataReader;
import com.elo.ranking.dao.MatchesFileDataReader;
import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.dao.PlayersFileDataReader;
import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.exception.InvalidPlayerNameException;
import com.elo.ranking.model.Match;
import com.elo.ranking.model.PlayerReport;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.elo.ranking.utils.TestUtils.mockedPlayer;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote (C62183)
 */
@ExtendWith(MockitoExtension.class)
class ReportGenerationStrategyTest {

    private final PlayersDataReader playersDataReader = Mockito.mock(PlayersFileDataReader.class);

    private final MatchesDataReader matchesDataReader = Mockito.mock(MatchesFileDataReader.class);

    @InjectMocks
    private ReportGenerationStrategy strategy;

    @Test
    void execute_ReturnsReportOfTheSpecifiedPlayer() throws EloRankingSystemException {
        when(playersDataReader.byName(anyString())).thenReturn(mockedPlayer(1));
        when(playersDataReader.byId(eq(2))).thenReturn(mockedPlayer(2));
        when(playersDataReader.byId(eq(3))).thenReturn(mockedPlayer(3));
        when(matchesDataReader.getMatches(anyInt())).thenReturn(mockedMatches());
        strategy.setPlayerName("Test Player1");
        final PlayerReport report =  strategy.execute();
        assertNotNull(report);
        assertNotNull(report.getMatchResults());
        assertEquals(2,report.getMatchResults().size());
        final PlayerReport.MatchResult result1 = report.getMatchResults().get(0);
        assertEquals(2, result1.getOpposition().getId());
        assertEquals("Won", result1.getResult());
        final PlayerReport.MatchResult result2 = report.getMatchResults().get(1);
        assertEquals(3, result2.getOpposition().getId());
        assertEquals("Lost", result2.getResult());
    }

    @Test
    void execute_ThrowsInvalidPlayerExceptionWhenPlayerNameIsNotValid() throws EloRankingSystemException {
        assertThrows(InvalidPlayerNameException.class, () -> strategy.execute());
    }

    @Test
    void execute_ThrowsPlayerNotFoundExceptionWhenPlayerDoesNotExistInSystem() throws EloRankingSystemException {
        when(playersDataReader.byName(anyString())).thenReturn(null);
        assertThrows(InvalidPlayerNameException.class, () -> strategy.execute());
    }

    private List<Match> mockedMatches() {
        final List<Match> matches = new ArrayList<>();
        matches.add(new Match(1,2));
        matches.add(new Match(3,1));
        return matches;
    }



}