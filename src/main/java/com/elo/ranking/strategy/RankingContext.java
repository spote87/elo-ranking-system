package com.elo.ranking.strategy;

import org.springframework.stereotype.Component;

import com.elo.ranking.exception.EloRankingSystemException;

import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Context class for {@link RankingStrategy}s.
 * @author Shivaji Pote
 */
@Component
@NoArgsConstructor
public class RankingContext<K> {

    @Setter
    private RankingStrategy strategy;

    public RankingContext(final RankingStrategy<K> strategy) {
        this.strategy = strategy;
    }

    public K executeStrategy() throws EloRankingSystemException {
        return  (K) strategy.execute();
    }

}
