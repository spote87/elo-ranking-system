package com.elo.ranking.dao;


import com.elo.ranking.model.Player;

import java.util.List;

/**
 * @author Shivaji Pote
 */
public interface PlayersDataReader {

    List<Player> readAll();

    Player byId(Integer playerId);

    Player byName(String name);
}
