package com.elo.ranking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Shivaji Pote
 */
@Log4j2
@RequiredArgsConstructor
@Component
public class RankServiceImpl implements RankService {

    //private final ScoreService scoreService;

    @Override
    public int getPlayerRank(final int playerId, final Map<Integer,Integer> scores) {
        //final Map<Integer, Integer> scores = scoreService.getAllScores();
        final SortedMap<Integer, List<Integer>> scoreRankMap = computeAndGetRanks(scores.values());
       final Optional<Map.Entry<Integer, List<Integer>>> rankOpt = scoreRankMap.entrySet().stream().filter(entry->entry.getValue().contains(scores.get(playerId))).findFirst();
       if(rankOpt.isPresent()){
           return rankOpt.get().getKey();
       }
       return 0;
    }

    @Override
    public SortedMap<Integer, List<Integer>> getAllPlayerRanks(final Map<Integer,Integer> scores) {
        //final Map<Integer, Integer> scores = scoreService.getAllScores();
        return computeAndGetRanks(scores.values());
    }

    /**
     * This method computes ranks based on scores. First it groups scores by it's occurrences. Then it sorts score and it's count map in descending order.
     * After ordering map, it iterates over it and computes ranks of each score.
     *
     * @param scores player scores
     * @return map of rank and list of scores at that rank
     */
    private SortedMap<Integer, List<Integer>> computeAndGetRanks(final Collection<Integer> scores) {
        final SortedMap<Integer, List<Integer>> ranks = new TreeMap<>();
        //this will create map of score and it's count like 5=2,10=1,0=5  etc
        final Map<Integer, Long> scoreCounts = scores.stream().filter(score -> score != null && score != 0).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
        final AtomicInteger rankCounter = new AtomicInteger(1);
        //below stream will sort scores by in reverse order and find out ranks of them
        final AtomicInteger previousKey = new AtomicInteger();
        scoreCounts.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).forEach((item) -> {
            rankPlayers(ranks, scoreCounts, rankCounter, previousKey, item);
        });
        //if there are 0 scores, put them at the end of the ranking
        ranks.put(rankCounter.getAndIncrement(),Arrays.asList(0));
        return ranks;
    }

    private void rankPlayers(final SortedMap<Integer, List<Integer>> ranks, final Map<Integer, Long> scoreCounts, final AtomicInteger rankCounter, final AtomicInteger previousKey, final Map.Entry<Integer, Long> item) {
        if (rankCounter.get() == 1) {
            newRank(ranks, rankCounter, item);
            //ranks.put(item.getKey(),rankCounter.getAndIncrement());
        } else {
            //get last inserted element
            final Long lastScoreCount = scoreCounts.get(previousKey.get());
            //scoreCounts.get(lastKey)==item.getValue()
            if (lastScoreCount==Long.valueOf(item.getValue())) {
              //if last score count and current score count is same, associate current score with last rank
                ranks.get(ranks.lastKey()).add(item.getKey());
            } else {
                newRank(ranks, rankCounter, item);
            }
        }
        previousKey.set(item.getKey());
    }

    private void newRank(final SortedMap<Integer, List<Integer>> ranks, final AtomicInteger rankCounter, final Map.Entry<Integer, Long> item) {
        final List<Integer> list = new ArrayList<>();
        list.add(item.getKey());
        ranks.put(rankCounter.getAndIncrement(), list);
    }
}
