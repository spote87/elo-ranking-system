package com.elo.ranking.exception;

/**
 * Exception class for invalid sorting strategy.
 * 
 * @author Shivaji Pote
 */
public class InvalidSortingStrategy extends EloRankingSystemException {
	private static final long serialVersionUID = 3576041409298033754L;

	/**
	 * {@link InvalidSortingStrategy} constructor
	 * 
	 * @param message exception message
	 */
	public InvalidSortingStrategy(final String message) {
		super(message);
	}
}
