import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.dao.PlayersFileDataReader;
import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.model.PlayerRank;
import com.elo.ranking.model.RankingRequest;
import com.elo.ranking.service.RankService;
import com.elo.ranking.service.RankServiceImpl;
import com.elo.ranking.service.ScoreService;
import com.elo.ranking.service.ScoreServiceImpl;
import com.elo.ranking.strategy.UserRankingStrategy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.elo.ranking.utils.TestUtils.mockedPlayer;
import static com.elo.ranking.utils.TestUtils.mockedPlayerScoreMap;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote
 */
@ExtendWith(MockitoExtension.class)
public class UserRankingStrategyTest {

    private final RankService rankingService = Mockito.mock(RankServiceImpl.class);

    private final ScoreService scoreService = Mockito.mock(ScoreServiceImpl.class);

    private final PlayersDataReader playersDataReader = Mockito.mock(PlayersFileDataReader.class);

    @InjectMocks
    private UserRankingStrategy userRankingStrategy;

    @Test
    public void testExecute_ReturnsRankingsOfAllPlayers() throws EloRankingSystemException {
        when(scoreService.getAllScores()).thenReturn(mockedPlayerScoreMap());
        when(rankingService.getPlayerRank(Mockito.eq(2), anyMap())).thenReturn(1);
        when(rankingService.getPlayerRank(Mockito.eq(8), anyMap())).thenReturn(1);
        when(rankingService.getPlayerRank(Mockito.eq(1), anyMap())).thenReturn(2);
        when(rankingService.getPlayerRank(Mockito.eq(6), anyMap())).thenReturn(2);
        when(rankingService.getPlayerRank(Mockito.eq(3), anyMap())).thenReturn(3);
        when(playersDataReader.byId(Mockito.eq(1))).thenReturn(mockedPlayer(1));
        final RankingRequest request = new RankingRequest();
        final List<PlayerRank> playerRanks = userRankingStrategy.execute(request);
        assertNotNull(playerRanks);
        assertEquals(6, playerRanks.size());
    }

    @Test
    public void testExecute_ReturnsRankingOfSpecifiedPlayer() throws EloRankingSystemException {
        when(scoreService.getAllScores()).thenReturn(mockedPlayerScoreMap());
        when(rankingService.getPlayerRank(Mockito.eq(2), anyMap())).thenReturn(1);
        when(rankingService.getPlayerRank(Mockito.anyInt(), anyMap())).thenReturn(2);
        when(playersDataReader.byName(Mockito.anyString())).thenReturn(mockedPlayer(2));
        final RankingRequest request = new RankingRequest();
        request.setPlayerName("Test Player2");
        final List<PlayerRank> playerRanks = userRankingStrategy.execute(request);
        assertNotNull(playerRanks);
        assertEquals(1, playerRanks.size());
    }

    @Test
    public void testExecute_ThrowsExceptionWhenPlayerDoesNotExistInSystem() throws EloRankingSystemException {
        when(scoreService.getAllScores()).thenReturn(mockedPlayerScoreMap());
        when(rankingService.getPlayerRank(Mockito.eq(2), anyMap())).thenReturn(1);
        when(rankingService.getPlayerRank(Mockito.anyInt(), anyMap())).thenReturn(2);
        when(playersDataReader.byName(Mockito.anyString())).thenReturn(null);
        final RankingRequest request = new RankingRequest();
        request.setPlayerName("Test Player2");
        assertThrows(EloRankingSystemException.class, () -> userRankingStrategy.execute(request));
    }

}
