package com.elo.ranking.strategy;

import com.elo.ranking.exception.EloRankingSystemException;

/**
 * @author Shivaji Pote
 */
public interface RankingStrategy<T,K> {

    K execute(T request) throws EloRankingSystemException;

}
