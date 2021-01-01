package com.elo.ranking.service;

import com.elo.ranking.builder.PlayerScoreCardBuilder;
import com.elo.ranking.dao.MatchesDataReader;
import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.model.PlayerScoreCard;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Shivaji Pote (C62183)
 */
@Component
@Log4j2
@RequiredArgsConstructor
public class ScoreCardGenerator {

    private final PlayersDataReader playersDataReader;

    private final MatchesDataReader matchesDataReader;

    private final ScoreService scoreService;

    private final RankService rankService;


    public List<PlayerScoreCard> getAll() {
        log.debug("Getting score card of all the players");
        final Map<Integer, Integer> playerScores = scoreService.getPlayerScores();
        final List<PlayerScoreCard> scoreCards = new ArrayList<>();
        playerScores.entrySet().forEach(player -> {
            final PlayerScoreCardBuilder builder = new PlayerScoreCardBuilder(new PlayerScoreCard());
            builder.player(playersDataReader.byId(player.getKey()));
            builder.rank(rankService.getPlayerRank(player.getKey(), playerScores));
            builder.score(player.getValue());
            builder.wins(player.getValue());//wins=score
            builder.losses(matchesDataReader.getMatches(player.getKey()).size() - player.getValue());
            scoreCards.add(builder.build());
        });
        return scoreCards;
    }
}
