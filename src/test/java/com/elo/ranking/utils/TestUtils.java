package com.elo.ranking.utils;

import com.elo.ranking.model.Match;
import com.elo.ranking.model.Player;
import com.elo.ranking.model.PlayerScoreCard;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Shivaji Pote
 */
public final class TestUtils {

    private TestUtils() throws InstantiationException {
        throw new InstantiationException("Cannot instantiate util class");
    }

    public static InputStream readResourceAsStream(final String resource) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
    }

    public static List<Match> mockedMatchesData() {
        final List<Match> matches = new ArrayList<>();
        //1=1 win,2 loses
        final Match match1 = new Match(2,1);
        matches.add(match1);
        final Match match2 = new Match(1,3);
        matches.add(match2);
        final Match match3 = new Match(1,4);
        matches.add(match3);
        //2=3 win,0 loses
        final Match match4 = new Match(2,4);
        matches.add(match4);
        final Match match5 = new Match(2,5);
        matches.add(match5);
        //3=0 win,1 loses
        final Match match6 = new Match(6,3);
        matches.add(match6);
        return matches;
    }

    public static List<Player> mockedPlayersData() {
        final List<Player> players = new ArrayList<>();
        final Player player1 = new Player(1, "Melodie");
        players.add(player1);
        final Player player2 = new Player(2, "Solange");
        players.add(player2);
        final Player player3 = new Player(3, "Johanne");
        players.add(player3);
        final Player player4 = new Player(4, "Bunny");
        players.add(player4);
        final Player player5 = new Player(5, "Tai");
        players.add(player5);
        final Player player6 = new Player(6, "Kami");
        players.add(player6);
        return players;
    }

    public static Map<Integer, Integer> mockedPlayerScoreMap() {
        final Map<Integer,Integer> scoreMap = new HashMap<>();
        //player id and score
        scoreMap.put(1,2);
        scoreMap.put(2,3);
        scoreMap.put(3,0);
        scoreMap.put(6, 1);
        scoreMap.put(7, 0);
        scoreMap.put(8, 3);
        return scoreMap;
    }

    public static Player mockedPlayer(final int playerId) {
        return new Player(playerId, "Test" + playerId);
    }

    public static List<PlayerScoreCard> mockedRankingData(final int noOfRecords) {
        final List<PlayerScoreCard> ranks = new ArrayList<>();
        int rank = noOfRecords;
        for (int i = 1; i <= noOfRecords; i++) {
            final Player player = new Player(i, "Test Player" + i);
            final PlayerScoreCard playerScoreCard = new PlayerScoreCard(player, i, rank--);
            ranks.add(playerScoreCard);
        }
        return ranks;
    }

    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads file and returns {@link InputStream} instance.
     *
     * @param fileName name of the file
     * @return {@code InputStream} instance
     */
    public static InputStream readFile(final String fileName) {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

}
