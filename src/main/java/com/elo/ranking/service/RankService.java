package com.elo.ranking.service;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * Service interface for computing ranks of the players.
 *
 * @author Shivaji Pote
 */
public interface RankService {

    /**
     * This method computes and returns rank of the specified player. Rank is computed based on provided <em>players</em> and <em>scores</em> map.
     *
     * @param playerId id of the player
     * @param scores Map containing player id and scores
     * @return rank of the requested player
     */
    int getPlayerRank(int playerId, Map<Integer,Integer> scores);

    /**
     * This method computes and returns rank of the all players. Rank is computed based on provided <em>players</em> and <em>scores</em> map.
     * @param scores Map containing player id and scores
     * @return map containing <em>rank</em> and list of <em>scores</em>
     */
    SortedMap<Integer, List<Integer>> getAllPlayerRanks(Map<Integer,Integer> scores);
}
