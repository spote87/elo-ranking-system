package com.elo.ranking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Model class for player's report.
 * 
 * @author Shivaji Pote
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerReport {

	private Player player;

	/**
	 * Map of player and result of the match
	 */
	private List<MatchResult> matchResults;

	/**
	 * Model class for match result holding opposition player data and result of the
	 * match.
	 * 
	 * @author Shivaji Pote
	 *
	 */
	@Setter
	@Getter
	public static class MatchResult {

		private Player opposition;

		private String result;
	}
}
