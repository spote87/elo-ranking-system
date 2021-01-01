package com.elo.ranking.service;

import java.util.Map;

/**
 * Score service interface which contains methods to handle scoring.
 *
 * @author Shivaji Pote
 */
public interface ScoreService {

	/**
	 * This method computes and returns score of particular player.
	 *
	 * @param playerId id of the player
	 * @return score of the player
	 */
	int getScore(int playerId);

	/**
	 * This method computes and returns scores of all the plauers.
	 *
	 * @return map of player id and their scores
	 */
	Map<Integer, Integer> getPlayerScores();

}
