package com.elo.ranking.strategy;

import com.elo.ranking.dao.MatchesDataReader;
import com.elo.ranking.dao.MatchesFileDataReader;
import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.dao.PlayersFileDataReader;
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
class SortByScoreStrategyTest {

    private final PlayersDataReader playersDataReader = Mockito.mock(PlayersFileDataReader.class);

    private final MatchesDataReader matchesDataReader = Mockito.mock(MatchesFileDataReader.class);

    private final ScoreService scoreService = Mockito.mock(ScoreServiceImpl.class);

    private final RankService rankService = Mockito.mock(RankServiceImpl.class);

    private final ScoreCardGenerator scoreCardGenerator = new ScoreCardGenerator(playersDataReader,matchesDataReader,scoreService,rankService);

    //  @InjectMocks
    private final SortByScoreStrategy sortByScoreStrategy = new SortByScoreStrategy(scoreCardGenerator);


    @Test
    void execute_SortsPlayerByTheirScores() {
        when(scoreService.getPlayerScores()).thenReturn(TestUtils.mockedPlayerScoreMap());
        when(playersDataReader.byId(Mockito.anyInt())).thenReturn(TestUtils.mockedPlayer(1));
        final List<PlayerScoreCard> scoreCards=sortByScoreStrategy.execute();
        assertNotNull(scoreCards);
        assertEquals(6, scoreCards.size());
        final PlayerScoreCard card1 = scoreCards.get(0);
        assertEquals(0, card1.getScore());
        final PlayerScoreCard card2 = scoreCards.get(1);
        assertEquals(0, card2.getScore());
        final PlayerScoreCard card3 = scoreCards.get(2);
        assertEquals(1, card3.getScore());
        final PlayerScoreCard card4 = scoreCards.get(3);
        assertEquals(2, card4.getScore());
    }

    @Test
    void execute_SortsPlayerInDescendingOrderByTheirScores() {
        when(scoreService.getPlayerScores()).thenReturn(TestUtils.mockedPlayerScoreMap());
        when(playersDataReader.byId(Mockito.anyInt())).thenReturn(TestUtils.mockedPlayer(1));
      //  sortByScoreStrategy = new SortByScoreStrategy(playersDataReader, scoreService);
        sortByScoreStrategy.setOrder("desc");
        final List<PlayerScoreCard> scoreCards=sortByScoreStrategy.execute();
        assertNotNull(scoreCards);
        assertEquals(6, scoreCards.size());
        final PlayerScoreCard card1 = scoreCards.get(0);
        assertEquals(3, card1.getScore());
        final PlayerScoreCard card2 = scoreCards.get(1);
        assertEquals(3, card2.getScore());
        final PlayerScoreCard card3 = scoreCards.get(2);
        assertEquals(2, card3.getScore());
    }
}