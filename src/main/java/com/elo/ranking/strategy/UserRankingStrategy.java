package com.elo.ranking.strategy;

import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.exception.PlayerNotFoundException;
import com.elo.ranking.model.Player;
import com.elo.ranking.model.PlayerRank;
import com.elo.ranking.model.RankingRequest;
import com.elo.ranking.service.RankService;
import com.elo.ranking.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * @author Shivaji Pote
 */
@Log4j2
@RequiredArgsConstructor
@Component("userRankingStrategy")
public class UserRankingStrategy implements RankingStrategy<RankingRequest, List<PlayerRank>> {

    private final RankService rankingService;

    private final ScoreService scoreService;

    private final PlayersDataReader playersDataReader;

    @Override
    public List<PlayerRank> execute(final RankingRequest request) throws EloRankingSystemException {
        final Map<Integer, Integer> scores = scoreService.getAllScores();
        if (request != null && StringUtils.hasText(request.getPlayerName())) {
            log.debug("Fetching ranking of player {}", request.getPlayerName());
            return Arrays.asList(getIndividualPlayerRank(request, scores));
        } else {
            log.debug("Fetching rankings of all players");
            return getAllPlayerRanks(scores);
        }
    }

    private List<PlayerRank> getAllPlayerRanks(final Map<Integer, Integer> scores) {
        final List<PlayerRank> playerRanks = new ArrayList<>();
        scores.entrySet().forEach(entry -> {
            final int rank = rankingService.getPlayerRank(entry.getKey(), scores);
            final PlayerRank playerRank = new PlayerRank(playersDataReader.byId(entry.getKey()), entry.getValue(), rank);
            playerRanks.add(playerRank);
        });
        return playerRanks;
    }

    private PlayerRank getIndividualPlayerRank(final RankingRequest request, final Map<Integer, Integer> scores) throws EloRankingSystemException {
        final Player player = playersDataReader.byName(request.getPlayerName());
        if (player == null) {
            throw new PlayerNotFoundException("Player " + request.getPlayerName() + " not found in system");
        }
        final int score = scoreService.getScore(player.getId());
        final int rank = rankingService.getPlayerRank(player.getId(), scores);
        return new PlayerRank(player, score, rank);
    }

}
