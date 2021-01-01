package com.elo.ranking.service;

import com.elo.ranking.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Map;
import java.util.SortedMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Shivaji Pote
 */
@ExtendWith(MockitoExtension.class)
class RankServiceImplTest {

  //  private final ScoreService scoreService = Mockito.mock(ScoreServiceImpl.class);

    @InjectMocks
    private RankServiceImpl rankService;

    @Test
    void getRank_ReturnsRankOfSpecifiedPlayer() {
        final Map<Integer, Integer> scores = TestUtils.mockedPlayerScoreMap();
        //when(scoreService.getPlayerScores()).thenReturn(scores);

        final int rank1 = rankService.getPlayerRank(2, scores);
        assertEquals(1,rank1);
        final int rank8 = rankService.getPlayerRank(8,scores);
        assertEquals(1,rank8);
        final int rank2 = rankService.getPlayerRank(1,scores);
        assertEquals(2,rank2);
        final int rank3 = rankService.getPlayerRank(6,scores);
        assertEquals(3, rank3);
        final int rank7 = rankService.getPlayerRank(7,scores);
        assertEquals(4, rank7);
        final int rank33 = rankService.getPlayerRank(3,scores);
        assertEquals(4, rank33);
    }

    @Test
    void getAllRanks_ReturnsRankingsOfAllPlayers() {
        //when(scoreService.getPlayerScores()).thenReturn(TestUtils.mockedPlayerScoreMap());
        final SortedMap<Integer, Integer> ranks = rankService.getAllPlayerRanks(TestUtils.mockedPlayerScoreMap());
        assertNotNull(ranks);
        assertFalse(ranks.isEmpty());
        final Integer rank1 = ranks.get(1);
        assertNotNull(rank1);
        //assertFalse(rank1.isEmpty());
        //score
        assertEquals(3, rank1);
        final Integer rank2 = ranks.get(2);
        assertNotNull(rank2);
        //assertFalse(rank2.isEmpty());
        //score
        assertEquals(2, rank2);
        final Integer rank3 = ranks.get(3);
        assertNotNull(rank3);
        //assertFalse(rank3.isEmpty());
        assertEquals(1, rank3);

        final Integer rank4 = ranks.get(4);
        assertNotNull(rank4);
        //assertFalse(rank3.isEmpty());
        assertEquals(0, rank4);
    }
}