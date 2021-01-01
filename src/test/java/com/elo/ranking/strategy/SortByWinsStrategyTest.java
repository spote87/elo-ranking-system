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
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote (C62183)
 */
@ExtendWith(MockitoExtension.class)
class SortByWinsStrategyTest {

    private final PlayersDataReader playersDataReader = Mockito.mock(PlayersFileDataReader.class);

    private final MatchesDataReader matchesDataReader = Mockito.mock(MatchesFileDataReader.class);

    private final ScoreService scoreService = Mockito.mock(ScoreServiceImpl.class);

    private final RankService rankService = Mockito.mock(RankServiceImpl.class);

    private final ScoreCardGenerator scoreCardGenerator = new ScoreCardGenerator(playersDataReader,matchesDataReader,scoreService,rankService);

    //  @InjectMocks
    private final SortByWinsStrategy strategy = new SortByWinsStrategy(scoreCardGenerator);

    @Test
    void execute_SortsPlayersByWinsInAscendingOrder() throws EloRankingSystemException {
        when(scoreService.getPlayerScores()).thenReturn(TestUtils.mockedPlayerScoreMap());
        when(playersDataReader.byId(Mockito.anyInt())).thenReturn(TestUtils.mockedPlayer(1));
        final List<PlayerScoreCard> scoreCards = strategy.execute();
        assertNotNull(scoreCards);
        assertEquals(6,scoreCards.size());
        final PlayerScoreCard card1 = scoreCards.get(5);
        assertEquals(3, card1.getWins());
        final PlayerScoreCard card2 = scoreCards.get(4);
        assertEquals(3, card2.getWins());
        final PlayerScoreCard card3 = scoreCards.get(3);
        assertEquals(2, card3.getWins());
        final PlayerScoreCard card4 = scoreCards.get(2);
        assertEquals(1, card4.getWins());
        final PlayerScoreCard card5 = scoreCards.get(1);
        assertEquals(0, card5.getWins());
        final PlayerScoreCard card6 = scoreCards.get(0);
        assertEquals(0, card6.getWins());
    }

    @Test
    void execute_SortsPlayersByWinsInDescendingOrder() throws EloRankingSystemException {
        when(scoreService.getPlayerScores()).thenReturn(TestUtils.mockedPlayerScoreMap());
        when(playersDataReader.byId(Mockito.anyInt())).thenReturn(TestUtils.mockedPlayer(1));
        strategy.setOrder("desc");
        final List<PlayerScoreCard> scoreCards = strategy.execute();
        assertNotNull(scoreCards);
        assertEquals(6,scoreCards.size());
        final PlayerScoreCard card1 = scoreCards.get(0);
        assertEquals(3, card1.getWins());
        final PlayerScoreCard card2 = scoreCards.get(1);
        assertEquals(3, card2.getWins());
        final PlayerScoreCard card3 = scoreCards.get(2);
        assertEquals(2, card3.getWins());
        final PlayerScoreCard card4 = scoreCards.get(3);
        assertEquals(1, card4.getWins());
        final PlayerScoreCard card5 = scoreCards.get(4);
        assertEquals(0, card5.getWins());
        final PlayerScoreCard card6 = scoreCards.get(5);
        assertEquals(0, card6.getWins());
    }
}