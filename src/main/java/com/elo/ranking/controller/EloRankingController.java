package com.elo.ranking.controller;

import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.model.PlayerReport;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.strategy.PlayerRankingStrategy;
import com.elo.ranking.strategy.RankingContext;
import com.elo.ranking.strategy.RankingStrategy;
import com.elo.ranking.strategy.ReportGenerationStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controller for ranking endpoints.
 * 
 * @author Shivaji Pote
 */
@RestController
@Log4j2
@RequiredArgsConstructor
public class EloRankingController {

	private final PlayerRankingStrategy playerRankingStrategy;

	private final RankingStrategy<List<PlayerScoreCard>> allUsersRankingStrategy;

	private final ReportGenerationStrategy reportGenerationStrategy;

	private final RankingContext<List<PlayerScoreCard>> rankingContext;

	/**
	 * This endpoint shows ranking of specified player if passed. Otherwise it shows
	 * rankings of all players.
	 * 
	 * @param playerName name of the player
	 * @return list of {@link PlayerScoreCard}
	 * @throws EloRankingSystemException if player name is invalid or player does
	 *                                   not exist in system
	 */
	@GetMapping(value = { "/rankings", "/rankings/{playerName}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlayerScoreCard>> showRanking(@PathVariable final Optional<String> playerName)
			throws EloRankingSystemException {
		final List<PlayerScoreCard> ranks;
		if (playerName.isPresent()) {
			final String player = playerName.get();
			log.debug("Fetching player {} rankings", player);
			playerRankingStrategy.setPlayerName(player);
			rankingContext.setStrategy(playerRankingStrategy);
			ranks = rankingContext.executeStrategy();
		} else {
			rankingContext.setStrategy(allUsersRankingStrategy);
			ranks = rankingContext.executeStrategy();
		}
		return ResponseEntity.ok(ranks);
	}

	/**
	 * This method returns report of specified player.
	 * 
	 * @param playerName name of the player
	 * @return {@link PlayerReport}
	 * @throws EloRankingSystemException if player name is invalid or player does
	 *                                   not exist in system
	 */
	@GetMapping(value = { "/report/{playerName}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PlayerReport> getReport(@PathVariable final Optional<String> playerName)
			throws EloRankingSystemException {
		if (playerName.isPresent()) {
			reportGenerationStrategy.setPlayerName(playerName.get());
		}
		rankingContext.setStrategy(reportGenerationStrategy);
		final PlayerReport report = (PlayerReport) rankingContext.executeStrategy();
		return ResponseEntity.ok(report);
	}
}
