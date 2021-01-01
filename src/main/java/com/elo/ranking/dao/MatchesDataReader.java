package com.elo.ranking.dao;

import com.elo.ranking.model.Match;

import java.util.List;

/**
 * @author Shivaji Pote
 */
public interface MatchesDataReader {

    /**
     * This method reads matches data from datasource.
     * @return list of {@link Match}s
     */
    List<Match> read();

    List<Match> getMatches(int playerId);
}
