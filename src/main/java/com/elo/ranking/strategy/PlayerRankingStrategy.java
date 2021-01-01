package com.elo.ranking.strategy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.exception.InvalidPlayerNameException;
import com.elo.ranking.exception.PlayerNotFoundException;
import com.elo.ranking.model.Player;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.service.RankService;
import com.elo.ranking.service.ScoreService;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

/**
 * Strategy class ranking specific player.
 * 
 * @author Shivaji Pote
 */
@Log4j2
@RequiredArgsConstructor
@Component
public class PlayerRankingStrategy implements RankingStrategy<List<PlayerScoreCard>> {

	private final RankService rankingService;

	private final ScoreService scoreService;

	private final PlayersDataReader playersDataReader;

	@Setter
	private String playerName;

	/**
	 * This method ranks and returns provided players data.
	 * @return list of {@link PlayerScoreCard}
	 */
	@Override
	public List<PlayerScoreCard> execute() throws EloRankingSystemException {
		if (StringUtils.hasText(playerName)) {
			final Map<Integer, Integer> scores = scoreService.getPlayerScores();
			log.debug("Fetching ranking of player {}", playerName);
			return Arrays.asList(getIndividualPlayerRank(playerName, scores));
		} else {
			throw new InvalidPlayerNameException("Invalid player name");
		}
	}

	private PlayerScoreCard getIndividualPlayerRank(final String playerName, final Map<Integer, Integer> scores)
			throws EloRankingSystemException {
		final Player player = playersDataReader.byName(playerName);
		if (player == null) {
			throw new PlayerNotFoundException("Player " + playerName + " not found in system");
		}
		final int score = scoreService.getScore(player.getId());
		final int rank = rankingService.getPlayerRank(player.getId(), scores);
		return new PlayerScoreCard(player, score, rank);
	}

}
