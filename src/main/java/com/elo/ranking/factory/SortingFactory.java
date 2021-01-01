package com.elo.ranking.factory;

import com.elo.ranking.exception.InvalidSortingStrategy;
import com.elo.ranking.strategy.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author Shivaji Pote (C62183)
 */
@Component
@RequiredArgsConstructor
public class SortingFactory {

    private final RankingStrategy playerSortingStrategy;

    private final SortByScoreStrategy sortByScoreStrategy;

    private final SortByRankingStrategy sortByRankingStrategy;

    private final SortByWinsStrategy sortByWinsStrategy;

    private final SortByLossesStrategy sortByLossesStrategy;

    public RankingStrategy getSortingStrategy(final String sortBy, final String order) throws InvalidSortingStrategy {
        if (StringUtils.hasText(sortBy)) {
            final RankingStrategy strategy;
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
