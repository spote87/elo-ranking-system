package com.elo.ranking.exception;

import lombok.Getter;

/**
 * Base exception class for Elo Ranking System.
 * 
 * @author Shivaji Pote
 */
public class EloRankingSystemException extends Exception {

	private static final long serialVersionUID = -4646915854073154406L;

	@Getter
	private final String message;

	/**
	 * {@link EloRankingSystemException} constructor.
	 * 
	 * @param message exception message
	 */
	public EloRankingSystemException(final String message) {
		this.message = message;
	}

}
