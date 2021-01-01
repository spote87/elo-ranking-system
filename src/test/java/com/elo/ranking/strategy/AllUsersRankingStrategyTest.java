package com.elo.ranking.strategy;

import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.dao.PlayersFileDataReader;
import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.service.RankService;
import com.elo.ranking.service.RankServiceImpl;
import com.elo.ranking.service.ScoreService;
import com.elo.ranking.service.ScoreServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static com.elo.ranking.utils.TestUtils.mockedPlayer;
import static com.elo.ranking.utils.TestUtils.mockedPlayerScoreMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote
 */
@ExtendWith(MockitoExtension.class)
class AllUsersRankingStrategyTest {

    private final RankService rankingService = Mockito.mock(RankServiceImpl.class);

    private final ScoreService scoreService = Mockito.mock(ScoreServiceImpl.class);

    private final PlayersDataReader playersDataReader = Mockito.mock(PlayersFileDataReader.class);

    @InjectMocks
    private AllUsersRankingStrategy allUsersRankingStrategy;

    @Test
    void testExecute_ReturnsRankingsOfAllPlayers() throws EloRankingSystemException {
        when(scoreService.getPlayerScores()).thenReturn(mockedPlayerScoreMap());
        when(rankingService.getPlayerRank(Mockito.eq(2), anyMap())).thenReturn(1);
        when(rankingService.getPlayerRank(Mockito.eq(8), anyMap())).thenReturn(1);
        when(rankingService.getPlayerRank(Mockito.eq(1), anyMap())).thenReturn(2);
        when(rankingService.getPlayerRank(Mockito.eq(6), anyMap())).thenReturn(2);
        when(rankingService.getPlayerRank(Mockito.eq(3), anyMap())).thenReturn(3);
        when(playersDataReader.byId(Mockito.eq(1))).thenReturn(mockedPlayer(1));
        final List<PlayerScoreCard> playerScoreCards = allUsersRankingStrategy.execute();
        assertNotNull(playerScoreCards);
        assertEquals(6, playerScoreCards.size());
    }

}