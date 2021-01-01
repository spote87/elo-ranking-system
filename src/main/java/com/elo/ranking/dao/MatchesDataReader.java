package com.elo.ranking.dao;

import com.elo.ranking.model.Match;

import java.util.List;

/**
 * Interface for reading matches data.
 * 
 * @author Shivaji Pote
 */
public interface MatchesDataReader {

	/**
	 * This method reads matches data from datasource.
	 * 
	 * @return list of {@link Match}s
	 */
	List<Match> read();

	/**
	 * This method returns matches for specified player
	 * 
	 * @param playerId id of the player
	 * @return list of {@link Match}s
	 */
	List<Match> getMatches(int playerId);
}
