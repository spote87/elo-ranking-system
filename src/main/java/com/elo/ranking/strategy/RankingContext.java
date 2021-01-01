package com.elo.ranking.strategy;

import com.elo.ranking.exception.EloRankingSystemException;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

/**
 * @author Shivaji Pote
 */
@Log4j2
@Component
@NoArgsConstructor
public class RankingContext<K> {

    @Setter
    private RankingStrategy strategy;

    public RankingContext(final RankingStrategy strategy) {
        this.strategy = strategy;
    }

    public K executeStrategy() throws EloRankingSystemException {
        return (K) strategy.execute();
    }

}
