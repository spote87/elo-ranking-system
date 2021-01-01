package com.elo.ranking.strategy;

import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.service.RankService;
import com.elo.ranking.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author Shivaji Pote
 */
@Log4j2
@RequiredArgsConstructor
@Component("allUsersRankingStrategy")
public class AllUsersRankingStrategy implements RankingStrategy<List<PlayerScoreCard>> {

    private final RankService rankingService;

    private final ScoreService scoreService;

    private final PlayersDataReader playersDataReader;

    @Override
    public List<PlayerScoreCard> execute() throws EloRankingSystemException {
        final Map<Integer, Integer> scores = scoreService.getPlayerScores();
        log.debug("Fetching rankings of all players");
        return getAllPlayerRanks(scores);
    }

    private List<PlayerScoreCard> getAllPlayerRanks(final Map<Integer, Integer> scores) {
        final List<PlayerScoreCard> playerScoreCards = new ArrayList<>();
        scores.entrySet().forEach(entry -> {
            final Integer rank = rankingService.getPlayerRank(entry.getKey(), scores);
            final PlayerScoreCard playerScoreCard = new PlayerScoreCard(playersDataReader.byId(entry.getKey()), entry.getValue(), rank);
            playerScoreCards.add(playerScoreCard);
        });
        return playerScoreCards;
    }

}
