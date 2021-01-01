package com.elo.ranking.builder;

import com.elo.ranking.model.Player;
import com.elo.ranking.model.PlayerScoreCard;
import lombok.extern.log4j.Log4j2;

/**
 * @author Shivaji Pote (C62183)
 */
@Log4j2
public class PlayerScoreCardBuilder {

    private final PlayerScoreCard scoreCard;

    public PlayerScoreCardBuilder(final PlayerScoreCard scoreCard) {
        this.scoreCard = scoreCard;
    }

    public PlayerScoreCardBuilder player(final Player player) {
        scoreCard.setPlayer(player);
        return this;
    }

    public PlayerScoreCard build() {
        log.debug("Building score card instance");
        return scoreCard;
    }

    public PlayerScoreCardBuilder score(final Integer score) {
        scoreCard.setScore(score);
        return this;
    }

    public PlayerScoreCardBuilder rank(final Integer rank) {
        scoreCard.setRank(rank);
        return this;
    }

    public PlayerScoreCardBuilder wins(final Integer wins) {
        scoreCard.setWins(wins);
        return this;
    }

    public PlayerScoreCardBuilder losses(final Integer losses) {
        scoreCard.setLosses(losses);
        return this;
    }
}
