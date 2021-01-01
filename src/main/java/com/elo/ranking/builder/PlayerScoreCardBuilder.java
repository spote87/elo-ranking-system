package com.elo.ranking.builder;

import com.elo.ranking.model.Player;
import com.elo.ranking.model.PlayerScoreCard;
import lombok.extern.log4j.Log4j2;

/**
 * {@link PlayerScoreCard} builder class.
 *
 * @author Shivaji Pote
 */
@Log4j2
public class PlayerScoreCardBuilder {

    private final PlayerScoreCard scoreCard;

    /**
     * {@link PlayerScoreCardBuilder} constructor
     *
     * @param scoreCard {@link PlayerScoreCard} instance
     */
    public PlayerScoreCardBuilder(final PlayerScoreCard scoreCard) {
        this.scoreCard = scoreCard;
    }

    /**
     * This method returns built {@link PlayerScoreCard} instance.
     *
     * @return <code>PlayerScoreCard</code> instance
     */
    public PlayerScoreCard build() {
        log.debug("Building score card instance");
        return scoreCard;
    }

    /**
     * This method updates {@link Player} instance in {@link PlayerScoreCardBuilder}.
     *
     * @param player instance of Player
     * @return PlayerScoreCardBuilder instance
     */
    public PlayerScoreCardBuilder player(final Player player) {
        scoreCard.setPlayer(player);
        return this;
    }

    /**
     * This method will update score in {@link PlayerScoreCardBuilder} instance
     *
     * @param score score to be updated
     * @return PlayerScoreCardBuilder instance
     */
    public PlayerScoreCardBuilder score(final Integer score) {
        scoreCard.setScore(score);
        return this;
    }

    /**
     * This method will update rank in {@link PlayerScoreCardBuilder}.
     *
     * @param rank rank of the player
     * @return PlayerScoreCardBuilder instance
     */
    public PlayerScoreCardBuilder rank(final Integer rank) {
        scoreCard.setRank(rank);
        return this;
    }

    /**
     * This method will update no.of wins in {@link PlayerScoreCard}.
     *
     * @param wins number of wins of the player
     * @return PlayerScoreCardBuilder instance
     */
    public PlayerScoreCardBuilder wins(final Integer wins) {
        scoreCard.setWins(wins);
        return this;
    }

    /**
     * This method will update number of lost matches in {@link Player}.
     *
     * @param losses number of losses
     * @return PlayerScoreCardBuilder instance
     */
    public PlayerScoreCardBuilder losses(final Integer losses) {
        scoreCard.setLosses(losses);
        return this;
    }
}
