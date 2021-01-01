package com.elo.ranking.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * {@link RankService} inplementation.
 *
 * @author Shivaji Pote
 */
@Log4j2
@RequiredArgsConstructor
@Component
public class RankServiceImpl implements RankService {

	@Override
	public int getPlayerRank(final int playerId, final Map<Integer, Integer> scores) {
		log.debug("Getting ranking for player {}", playerId);
		final SortedMap<Integer, Integer> scoreRankMap = computeAndGetRanks(scores.values());
		final Optional<Map.Entry<Integer, Integer>> rankOpt = scoreRankMap.entrySet().stream()
				.filter(entry -> entry.getValue().equals(scores.get(playerId))).findFirst();
		if (rankOpt.isPresent()) {
			return rankOpt.get().getKey();
		}
		return 0;
	}

	@Override
	public SortedMap<Integer, Integer> getAllPlayerRanks(final Map<Integer, Integer> scores) {
		log.debug("Getting rankings of all players");
		return computeAndGetRanks(scores.values());
	}

	/**
	 * This method computes ranks based on scores. First it sorts scores in reverse
	 * order and then computes rank based on score occurrences.
	 *
	 * @param scores player scores
	 * @return map of rank and score at that rank
	 */
	private SortedMap<Integer, Integer> computeAndGetRanks(final Collection<Integer> scores) {
		final SortedMap<Integer, Integer> ranks = new TreeMap<>();
		final AtomicInteger rankCounter = new AtomicInteger(1);
		final AtomicInteger prevScore = new AtomicInteger();
		scores.stream().sorted((a, b) -> b - a).forEach((score) -> {
			rankPlayers(ranks, scores, rankCounter, prevScore, score);
		});
		return ranks;
	}

	private void rankPlayers(final SortedMap<Integer, Integer> ranks, final Collection<Integer> scores,
							 final AtomicInteger rankCounter, final AtomicInteger previousScore, final Integer score) {
		if (rankCounter.get() == 1) {
			newRank(ranks, rankCounter, score);
		} else {
			if (previousScore.get() != score) {
				newRank(ranks, rankCounter, score);
			}
		}
		previousScore.set(score);
	}

	private void newRank(final SortedMap<Integer, Integer> ranks, final AtomicInteger rankCounter,
						 final Integer score) {
		ranks.put(rankCounter.getAndIncrement(), score);
	}
}
