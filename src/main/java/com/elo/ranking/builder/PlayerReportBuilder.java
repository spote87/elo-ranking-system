package com.elo.ranking.builder;

import com.elo.ranking.model.Player;
import com.elo.ranking.model.PlayerReport;
import com.elo.ranking.model.PlayerReport.MatchResult;

import java.util.List;

/**
 * @author Shivaji Pote (C62183)
 */
public class PlayerReportBuilder {

    private final PlayerReport playerReport;

    public PlayerReportBuilder(final PlayerReport report) {
        playerReport = report;
    }

    public PlayerReport build() {
        return playerReport;
    }

    public PlayerReportBuilder player(final Player player) {
        playerReport.setPlayer(player);
        return this;
    }

    public PlayerReportBuilder matchResults(final List<MatchResult> result) {
        playerReport.setMatchResults(result);
        return this;
    }

}
