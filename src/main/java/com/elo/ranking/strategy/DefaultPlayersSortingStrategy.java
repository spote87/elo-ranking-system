package com.elo.ranking.strategy;

import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Strategy class for sorting players data on player id.
 * 
 * @author Shivaji Pote
 */
@RequiredArgsConstructor
@Component("playerSortingStrategy")
public class DefaultPlayersSortingStrategy implements RankingStrategy<List<Player>> {

	private final PlayersDataReader playersDataReader;

	/**
	 * This method sorts players on player id and returns the player list.
	 * @return list of {@link Player}s
	 */
	@Override
	public List<Player> execute() {
		return playersDataReader.readAll().stream().sorted().collect(Collectors.toList());
	}
}
