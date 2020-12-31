package com.elo.ranking.controller;

import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.model.PlayersSortRequest;
import com.elo.ranking.model.RankingRequest;
import com.elo.ranking.strategy.RankingContext;
import com.elo.ranking.strategy.RankingStrategy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

/**
 * @author Shivaji Pote
 */
@RestController
@Log4j2
public class EloRankingController {

    @Autowired
    private RankingStrategy userRankingStrategy;

    @Autowired
    private RankingStrategy playerSortingStrategy;

    @Autowired
    private RankingContext rankingContext;


    @GetMapping(value = {"/rankings", "/rankings/{playerName}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> showRanking(@PathVariable("playerName") final Optional<String> playerName) throws EloRankingSystemException {
        final RankingRequest request = new RankingRequest();
        if (playerName.isPresent()) {
            final String user = playerName.get();
            request.setPlayerName(user);
            log.debug("Fetching user {} rankings");
        }
        rankingContext.setStrategy(userRankingStrategy);
        return ResponseEntity.ok(rankingContext.executeStrategy(request));
    }

    @GetMapping(path = "/players")
    public ResponseEntity<?> listPlayers(@RequestParam("sort_by") final String sortedBy, @RequestParam("order") final String order) throws EloRankingSystemException {
        final PlayersSortRequest request = new PlayersSortRequest();
        request.setSortBy(sortedBy);
        request.setOrder(order);
        rankingContext.setStrategy(playerSortingStrategy);
        rankingContext.executeStrategy(request);
        return null;
    }
}
