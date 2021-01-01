package com.elo.ranking.strategy;

import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.service.ScoreCardGenerator;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shivaji Pote (C62183)
 */
@RequiredArgsConstructor
@Component
public class SortByLossesStrategy implements RankingStrategy<List<PlayerScoreCard>> {

    @Setter
    private String order;

    private final ScoreCardGenerator scoreCardGenerator;

    @Override
    public List<PlayerScoreCard> execute() throws EloRankingSystemException {
        final List<PlayerScoreCard> scoreCards = scoreCardGenerator.getAll();
        return scoreCards.stream().sorted(getComparator()).collect(Collectors.toList());
    }

    private Comparator<PlayerScoreCard> getComparator() {
        return (StringUtils.hasText(order) && order.equalsIgnoreCase("desc")) ? Comparator.comparingInt(PlayerScoreCard::getLosses).reversed() : Comparator.comparingInt(PlayerScoreCard::getLosses);
    }
}