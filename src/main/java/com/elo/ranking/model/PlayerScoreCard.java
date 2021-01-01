package com.elo.ranking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for player's score card.
 * 
 * @author Shivaji Pote
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerScoreCard {

	/**
	 * {@link PlayerScoreCard} constructor.
	 * 
	 * @param player {@link Player} instance
	 * @param score  score of the player
	 * @param rank   rank of the player
	 */
	public PlayerScoreCard(final Player player, final Integer score, final Integer rank) {
		this.player = player;
		this.score = score;
		this.rank = rank;
	}

	private Player player;

	private Integer score;

	private Integer rank;

	private Integer wins;

	private Integer losses;

}
