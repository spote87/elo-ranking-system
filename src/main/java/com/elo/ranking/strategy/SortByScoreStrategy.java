package com.elo.ranking.strategy;

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
 * Strategy class for sorting by score.
 * @author Shivaji Pote
 */
@RequiredArgsConstructor
@Component//("sortByScoreStrategy")
public class SortByScoreStrategy implements RankingStrategy<List<PlayerScoreCard>> {


    private final ScoreCardGenerator scoreCardGenerator;

    @Setter
    private String order;

    /**
     * This method sorts and returns players data by their scores.
     * @return list of {@link PlayerScoreCard}s
     */
    @Override
    public List<PlayerScoreCard> execute() {
        final List<PlayerScoreCard> scoreCards = scoreCardGenerator.getAll();
       return scoreCards.stream().sorted(getComparator()).collect(Collectors.toList());
    }

    private Comparator<PlayerScoreCard> getComparator() {
        return (StringUtils.hasText(order) && order.equalsIgnoreCase("desc")) ? Comparator.comparingInt(PlayerScoreCard::getScore).reversed() : Comparator.comparingInt(PlayerScoreCard::getScore);
    }

}
