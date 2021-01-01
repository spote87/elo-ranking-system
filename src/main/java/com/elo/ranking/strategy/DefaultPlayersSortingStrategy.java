package com.elo.ranking.strategy;

import com.elo.ranking.dao.PlayersDataReader;
import com.elo.ranking.model.Player;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Shivaji Pote
 */
@RequiredArgsConstructor
@Component("playerSortingStrategy")
public class DefaultPlayersSortingStrategy implements RankingStrategy<List<Player>> {

    private final PlayersDataReader playersDataReader;


    @Override
    public List<Player> execute() {
        final List<Player> players = new ArrayList<>();
        return playersDataReader.readAll().stream().sorted().collect(Collectors.toList());
    }
}
