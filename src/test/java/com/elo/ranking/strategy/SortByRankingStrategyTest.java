package com.elo.ranking.strategy;

import com.elo.ranking.dao.MatchesDataReader;
import com.elo.ranking.dao.MatchesFileDataReader;
import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.dao.PlayersFileDataReader;
import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.service.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.elo.ranking.utils.TestUtils.mockedPlayer;
import static com.elo.ranking.utils.TestUtils.mockedPlayerScoreMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote
 */
@ExtendWith(MockitoExtension.class)
class SortByRankingStrategyTest {

    private final PlayersDataReader playersDataReader = Mockito.mock(PlayersFileDataReader.class);

    private final MatchesDataReader matchesDataReader = Mockito.mock(MatchesFileDataReader.class);

    private final ScoreService scoreService = Mockito.mock(ScoreServiceImpl.class);

    private final RankService rankService = Mockito.mock(RankServiceImpl.class);

    private final ScoreCardGenerator scoreCardGenerator = new ScoreCardGenerator(playersDataReader,matchesDataReader,scoreService,rankService);

    //  @InjectMocks
    private final SortByRankingStrategy sortByRankingStrategy = new SortByRankingStrategy(scoreCardGenerator);

    @Test
    void execute_SortsPlayersByTheirRankingInAscendingOrder() throws EloRankingSystemException {
        when(scoreService.getPlayerScores()).thenReturn(mockedPlayerScoreMap());
        when(playersDataReader.byId(anyInt())).thenReturn(mockedPlayer(1));
        mockPlayerRankingData();
        final List<PlayerScoreCard> scoreCards = sortByRankingStrategy.execute();
        assertNotNull(scoreCards);
        assertEquals(6,scoreCards.size());
        final PlayerScoreCard card1 = scoreCards.get(0);
        assertEquals(1,card1.getRank());
        assertEquals(3,card1.getScore());
        final PlayerScoreCard card2 = scoreCards.get(1);
        assertEquals(1,card2.getRank());
        assertEquals(3,card2.getScore());
        final PlayerScoreCard card3 = scoreCards.get(2);
        assertEquals(2,card3.getRank());
        assertEquals(2,card3.getScore());
        final PlayerScoreCard card4 = scoreCards.get(3);
        assertEquals(3,card4.getRank());
        assertEquals(1,card4.getScore());
        final PlayerScoreCard card5 = scoreCards.get(4);
        assertEquals(4,card5.getRank());
        assertEquals(0,card5.getScore());
        final PlayerScoreCard card6 = scoreCards.get(5);
        assertEquals(4,card6.getRank());
        assertEquals(0,card6.getScore());
    }

    @Test
    void execute_SortsPlayersByTheirRanksInDescendingOrder() throws EloRankingSystemException {
        when(scoreService.getPlayerScores()).thenReturn(mockedPlayerScoreMap());
        when(playersDataReader.byId(anyInt())).thenReturn(mockedPlayer(1));
        mockPlayerRankingData();
        sortByRankingStrategy.setOrder("desc");
        final List<PlayerScoreCard> scoreCards = sortByRankingStrategy.execute();
        assertNotNull(scoreCards);
        assertEquals(6,scoreCards.size());
        final PlayerScoreCard card1 = scoreCards.get(5);
        assertEquals(1,card1.getRank());
        assertEquals(3,card1.getScore());
        final PlayerScoreCard card2 = scoreCards.get(4);
        assertEquals(1,card2.getRank());
        assertEquals(3,card2.getScore());
        final PlayerScoreCard card3 = scoreCards.get(3);
        assertEquals(2,card3.getRank());
        assertEquals(2,card3.getScore());
        final PlayerScoreCard card4 = scoreCards.get(2);
        assertEquals(3,card4.getRank());
        assertEquals(1,card4.getScore());
        final PlayerScoreCard card5 = scoreCards.get(1);
        assertEquals(4,card5.getRank());
        assertEquals(0,card5.getScore());
        final PlayerScoreCard card6 = scoreCards.get(0);
        assertEquals(4,card6.getRank());
        assertEquals(0,card6.getScore());
    }

    private void mockPlayerRankingData() {
        when(rankService.getPlayerRank(Mockito.eq(1), Mockito.anyMap())).thenReturn(2);
        when(rankService.getPlayerRank(Mockito.eq(2), Mockito.anyMap())).thenReturn(1);
        when(rankService.getPlayerRank(Mockito.eq(8), Mockito.anyMap())).thenReturn(1);
        when(rankService.getPlayerRank(Mockito.eq(6), Mockito.anyMap())).thenReturn(3);
        when(rankService.getPlayerRank(Mockito.eq(7), Mockito.anyMap())).thenReturn(4);
        when(rankService.getPlayerRank(Mockito.eq(3), Mockito.anyMap())).thenReturn(4);
    }
}