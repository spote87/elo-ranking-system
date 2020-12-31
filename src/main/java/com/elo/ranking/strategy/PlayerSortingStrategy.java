package com.elo.ranking.strategy;

import com.elo.ranking.model.Player;
import com.elo.ranking.model.PlayersSortRequest;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Shivaji Pote
 */
@Component("playerSortingStrategy")
public class PlayerSortingStrategy implements RankingStrategy<PlayersSortRequest, List<Player>>{

    @Override
    public List<Player> execute(final PlayersSortRequest request) {
        //TODO implement me
        return  null;
    }
}
