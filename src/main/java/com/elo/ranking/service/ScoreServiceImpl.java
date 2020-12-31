package com.elo.ranking.service;

import com.elo.ranking.dao.MatchesDataReader;
import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.model.Match;
import com.elo.ranking.model.Player;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shivaji Pote
 */
@Service
@RequiredArgsConstructor
@Log4j2
public class ScoreServiceImpl implements ScoreService {

    private final MatchesDataReader matchesFileDataReader;

    private final PlayersDataReader playersFileDataReader;


    @Override
    public int getScore(final int playerId) {
        log.debug("Getting score of player {}", playerId);
        return getWinCount(playerId, matchesFileDataReader.read());
    }

    @Override
    public Map<Integer, Integer> getAllScores() {
        final List<Player> players = playersFileDataReader.readAll();
        final Map<Integer, Integer> scoreMap = new HashMap<>();
        for (final Player player : players) {
            scoreMap.put(player.getId(), getScore(player.getId()));
        }
        return scoreMap;
    }

    private int getWinCount(final int playerId, final List<Match> matches) {
        return (int) matches.stream().filter(match -> match.getWinner() == playerId).count();
    }
}
