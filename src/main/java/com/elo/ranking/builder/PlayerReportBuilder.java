package com.elo.ranking.builder;

import com.elo.ranking.model.Player;
import com.elo.ranking.model.PlayerReport;
import com.elo.ranking.model.PlayerReport.MatchResult;

import java.util.List;

/**
 * {@link PlayerReport} builder class.
 *
 * @author Shivaji Pote
 */
public class PlayerReportBuilder {

    private final PlayerReport playerReport;

    /**
     * {@link PlayerReportBuilder} constructor
     *
     * @param report instance of {@link PlayerReport}
     */
    public PlayerReportBuilder(final PlayerReport report) {
        playerReport = report;
    }

    /**
     * This method will return {@link PlayerReport} instance.
     *
     * @return <code>PlayerReport</code> instance
     */
    public PlayerReport build() {
        return playerReport;
    }

    /**
     * Updated {@link Player} instance in {@link PlayerReport}.
     *
     * @param player instance of Player
     * @return PlayerReportBuilder instance
     */
    public PlayerReportBuilder player(final Player player) {
        playerReport.setPlayer(player);
        return this;
    }

    /**
     * This method will update match results in {@link PlayerReport}.
     *
     * @param result list of {@link MatchResult}s
     * @return PlayerReportBuilder instance
     */
    public PlayerReportBuilder matchResults(final List<MatchResult> result) {
        playerReport.setMatchResults(result);
        return this;
    }

}
