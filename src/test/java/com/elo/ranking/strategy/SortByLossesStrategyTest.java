package com.elo.ranking.strategy;

import com.elo.ranking.dao.MatchesDataReader;
import com.elo.ranking.dao.MatchesFileDataReader;
import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.dao.PlayersFileDataReader;
import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.service.*;
import com.elo.ranking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote
 */
@ExtendWith(MockitoExtension.class)
class SortByLossesStrategyTest {

    private final PlayersDataReader playersDataReader = Mockito.mock(PlayersFileDataReader.class);

    private final MatchesDataReader matchesDataReader = Mockito.mock(MatchesFileDataReader.class);

    private final ScoreService scoreService = Mockito.mock(ScoreServiceImpl.class);

    private final RankService rankService = Mockito.mock(RankServiceImpl.class);

    private final ScoreCardGenerator scoreCardGenerator = new ScoreCardGenerator(playersDataReader,matchesDataReader,scoreService,rankService);

  //  @InjectMocks
    private final SortByLossesStrategy strategy = new SortByLossesStrategy(scoreCardGenerator);

    @Test
    void execute_SortsPlayersByLossesInAscendingOrder() throws EloRankingSystemException {
        when(scoreService.getPlayerScores()).thenReturn(TestUtils.mockedPlayerScoreMap());
        when(matchesDataReader.getMatches(anyInt())).thenReturn(TestUtils.mockedMatchesData());
        when(playersDataReader.byId(anyInt())).thenReturn(TestUtils.mockedPlayer(1));
        final List<PlayerScoreCard> scoreCards = strategy.execute();
        assertNotNull(scoreCards);
        assertEquals(6,scoreCards.size());
        final PlayerScoreCard card1 = scoreCards.get(0);
        assertEquals(3, card1.getLosses());
        final PlayerScoreCard card2 = scoreCards.get(1);
        assertEquals(3, card2.getLosses());
        final PlayerScoreCard card3 = scoreCards.get(2);
        assertEquals(4, card3.getLosses());
        final PlayerScoreCard card4 = scoreCards.get(3);
        assertEquals(5, card4.getLosses());
        final PlayerScoreCard card5 = scoreCards.get(4);
        assertEquals(6, card5.getLosses());
        final PlayerScoreCard card6 = scoreCards.get(5);
        assertEquals(6, card6.getLosses());
    }

    @Test
    void execute_SortsPlayersByLossesInDescendingOrder() throws EloRankingSystemException {
        when(scoreService.getPlayerScores()).thenReturn(TestUtils.mockedPlayerScoreMap());
        when(matchesDataReader.getMatches(anyInt())).thenReturn(TestUtils.mockedMatchesData());
        when(playersDataReader.byId(anyInt())).thenReturn(TestUtils.mockedPlayer(1));
        strategy.setOrder("desc");
        final List<PlayerScoreCard> scoreCards = strategy.execute();
        assertNotNull(scoreCards);
        assertEquals(6,scoreCards.size());
        final PlayerScoreCard card1 = scoreCards.get(5);
        assertEquals(3, card1.getLosses());
        final PlayerScoreCard card2 = scoreCards.get(4);
        assertEquals(3, card2.getLosses());
        final PlayerScoreCard card3 = scoreCards.get(3);
        assertEquals(4, card3.getLosses());
        final PlayerScoreCard card4 = scoreCards.get(2);
        assertEquals(5, card4.getLosses());
        final PlayerScoreCard card5 = scoreCards.get(1);
        assertEquals(6, card5.getLosses());
        final PlayerScoreCard card6 = scoreCards.get(0);
        assertEquals(6, card6.getLosses());
    }
}