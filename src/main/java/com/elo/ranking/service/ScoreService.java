package com.elo.ranking.service;

import java.util.Map;

/**
 * @author Shivaji Pote
 */
public interface ScoreService {

    int getScore(int playerId);

    Map<Integer, Integer> getAllScores();

}
