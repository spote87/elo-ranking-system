package com.elo.ranking.strategy;

import com.elo.ranking.builder.PlayerReportBuilder;
import com.elo.ranking.dao.MatchesDataReader;
import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.exception.InvalidPlayerNameException;
import com.elo.ranking.exception.PlayerNotFoundException;
import com.elo.ranking.model.Match;
import com.elo.ranking.model.Player;
import com.elo.ranking.model.PlayerReport;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Strategy class for report generation.
 * 
 * @author Shivaji Pote
 */
@RequiredArgsConstructor
@Component
public class ReportGenerationStrategy implements RankingStrategy<PlayerReport> {

	@Setter
	private String playerName;

	private final PlayersDataReader playersDataReader;

	private final MatchesDataReader matchesDataReader;

	/**
	 * This method generates report for provided player.
	 * 
	 * @return {@link PlayerReport} instance
	 * @throws EloRankingSystemException if player name is invalid or player does
	 *                                   not exist in system
	 */
	@Override
	public PlayerReport execute() throws EloRankingSystemException {
		if (StringUtils.hasText(playerName)) {
			final Player player = playersDataReader.byName(playerName);
			if (player == null) {
				throw new PlayerNotFoundException("Player not found in system");
			}
			return getPlayerReport(player);
		}
		throw new InvalidPlayerNameException("Invalid input player name");
	}

	private PlayerReport getPlayerReport(final Player player) {
		final PlayerReportBuilder builder = new PlayerReportBuilder(new PlayerReport());
		builder.player(player);
		final List<Match> matches = matchesDataReader.getMatches(player.getId());
		final List<PlayerReport.MatchResult> results = new ArrayList<>();
		matches.forEach(match -> {
			final PlayerReport.MatchResult matchResult = new PlayerReport.MatchResult();
			if (match.getWinner() == player.getId()) {
				matchResult.setResult("Won");
				matchResult.setOpposition(playersDataReader.byId(match.getLoser()));
			} else {
				matchResult.setResult("Lost");
				matchResult.setOpposition(playersDataReader.byId(match.getWinner()));
			}
			results.add(matchResult);
		});
		builder.matchResults(results);
		return builder.build();
	}
}
