package com.elo.ranking.dao;

import com.elo.ranking.model.Player;

import java.util.List;

/**
 * Interface for reading players data.
 * 
 * @author Shivaji Pote
 */
public interface PlayersDataReader {

	/**
	 * This method reads all players data.
	 * 
	 * @return list of {@link Player}s
	 */
	List<Player> readAll();

	/**
	 * This method returns single player based on player id.
	 * 
	 * @param playerId id of the player
	 * @return {@link Player} instance of found. Otherwise null
	 */
	Player byId(Integer playerId);

	/**
	 * This method returns single player based on player name.
	 * 
	 * @param name name of the player
	 * @return {@link Player} instance of found. Otherwise null
	 */
	Player byName(String name);
}
