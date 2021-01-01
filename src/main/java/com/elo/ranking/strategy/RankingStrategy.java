package com.elo.ranking.strategy;

import com.elo.ranking.exception.EloRankingSystemException;

/**
 * @author Shivaji Pote
 */
public interface RankingStrategy<K> {

    K execute() throws EloRankingSystemException;

}
