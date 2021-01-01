package com.elo.ranking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.factory.SortingFactory;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.strategy.RankingContext;

import lombok.RequiredArgsConstructor;

/**
 * Controller class containing sorting endpoints.
 * 
 * @author Shivaji Pote
 */
@RestController
@RequiredArgsConstructor
public class EloSortingController {

	@Autowired
	private final RankingContext<List<PlayerScoreCard>> rankingContext;

	@Autowired
	private final SortingFactory sortingFactory;

	@GetMapping(value = "/players", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<PlayerScoreCard>> listPlayers(
			@RequestParam(value = "sortBy", required = false) final String sortBy,
			@RequestParam(value = "order", required = false) final String order) throws EloRankingSystemException {
		rankingContext.setStrategy(sortingFactory.getSortingStrategy(sortBy, order));
		return ResponseEntity.ok(rankingContext.executeStrategy());
	}
}
