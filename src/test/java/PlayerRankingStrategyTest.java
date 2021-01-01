import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.dao.PlayersFileDataReader;
import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.exception.InvalidPlayerNameException;
import com.elo.ranking.exception.PlayerNotFoundException;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.service.RankService;
import com.elo.ranking.service.RankServiceImpl;
import com.elo.ranking.service.ScoreService;
import com.elo.ranking.service.ScoreServiceImpl;
import com.elo.ranking.strategy.PlayerRankingStrategy;
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
public class PlayerRankingStrategyTest {

    private final RankService rankingService = Mockito.mock(RankServiceImpl.class);

    private final ScoreService scoreService = Mockito.mock(ScoreServiceImpl.class);

    private final PlayersDataReader playersDataReader = Mockito.mock(PlayersFileDataReader.class);

    @InjectMocks
    private PlayerRankingStrategy userRankingStrategy;


    @Test
    public void testExecute_ReturnsRankingOfSpecifiedPlayer() throws EloRankingSystemException {
        when(scoreService.getPlayerScores()).thenReturn(mockedPlayerScoreMap());
        when(rankingService.getPlayerRank(Mockito.eq(2), anyMap())).thenReturn(1);
        when(rankingService.getPlayerRank(Mockito.anyInt(), anyMap())).thenReturn(2);
        when(playersDataReader.byName(Mockito.anyString())).thenReturn(mockedPlayer(2));
        userRankingStrategy.setPlayerName("Test Player2");
        final List<PlayerScoreCard> playerScoreCards = userRankingStrategy.execute();
        assertNotNull(playerScoreCards);
        assertEquals(1, playerScoreCards.size());
    }

    @Test
    public void testExecute_ThrowsExceptionWhenPlayerDoesNotExistInSystem() throws EloRankingSystemException {
        when(scoreService.getPlayerScores()).thenReturn(mockedPlayerScoreMap());
        when(rankingService.getPlayerRank(Mockito.eq(2), anyMap())).thenReturn(1);
        when(rankingService.getPlayerRank(Mockito.anyInt(), anyMap())).thenReturn(2);
        when(playersDataReader.byName(Mockito.anyString())).thenReturn(null);
        userRankingStrategy.setPlayerName("Test Player2");
        assertThrows(PlayerNotFoundException.class, () -> userRankingStrategy.execute());
    }

    @Test
    public void testExecute_ThrowsExceptionWhenPlayerNameNotSet() throws EloRankingSystemException {
        assertThrows(InvalidPlayerNameException.class, () -> userRankingStrategy.execute());
    }

}
