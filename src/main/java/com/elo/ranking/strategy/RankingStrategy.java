package com.elo.ranking.strategy;

import com.elo.ranking.exception.EloRankingSystemException;

/**
 * Ranking strategy interface.
 * 
 * @author Shivaji Pote
 */
public interface RankingStrategy<K> {

	/**
	 * This method will execute the strategy.
	 * 
	 * @return generic response. Each strategy is independent to chose return type
	 * @throws EloRankingSystemException if something goes wrong while executing
	 *                                   strategy
	 */
	K execute() throws EloRankingSystemException;

}
