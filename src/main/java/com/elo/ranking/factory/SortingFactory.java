package com.elo.ranking.factory;

import com.elo.ranking.exception.InvalidSortingStrategy;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.strategy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Factory class for determining sorting strategy. Class is not made abstract
 * because we want to take advantage of spring's DI.
 * 
 * @author Shivaji Pote
 */
@Component
@RequiredArgsConstructor
public class SortingFactory {

	private final RankingStrategy playerSortingStrategy;

	private final SortByScoreStrategy sortByScoreStrategy;

	private final SortByRankingStrategy sortByRankingStrategy;

	private final SortByWinsStrategy sortByWinsStrategy;

	private final SortByLossesStrategy sortByLossesStrategy;

	/**
	 * Factory method which returns {@link RankingStrategy} instance based on
	 * {@code sortBy} parameter value. This method is not made static because we
	 * want to use spring's DI feature,
	 * 
	 * @param sortBy sorting on field
	 * @param order  {@code asc} for ascending and {@code desc} for descending order
	 * @return {@link RankingStrategy} instance
	 * @throws InvalidSortingStrategy if sortBy value is not valid
	 */
	public RankingStrategy<List<PlayerScoreCard>> getSortingStrategy(final String sortBy, final String order)
			throws InvalidSortingStrategy {
		if (StringUtils.hasText(sortBy)) {
			final RankingStrategy<List<PlayerScoreCard>> strategy;
			switch (sortBy) {
			case "score":
				sortByScoreStrategy.setOrder(order);
				strategy = sortByScoreStrategy;
				break;
			case "ranking":
				sortByRankingStrategy.setOrder(order);
				strategy = sortByRankingStrategy;
				break;
			case "wins":
				sortByWinsStrategy.setOrder(order);
				strategy = sortByWinsStrategy;
				break;
			case "losses":
				sortByLossesStrategy.setOrder(order);
				strategy = sortByLossesStrategy;
				break;
			default:
				throw new InvalidSortingStrategy("Unexpected value: " + sortBy);
			}
			return strategy;
		} else {
			return playerSortingStrategy;
		}
	}
}
