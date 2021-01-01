package com.elo.ranking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for matches data.
 * 
 * @author Shivaji Pote
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Match {

	private int winner;

	private int loser;

}
