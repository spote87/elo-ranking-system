package com.elo.ranking.exception;

/**
 * Exception class for player not found in system.
 * 
 * @author Shivaji Pote
 */
public class PlayerNotFoundException extends EloRankingSystemException {

	private static final long serialVersionUID = -2081504112282077965L;

	/**
	 * {@link PlayerNotFoundException} constructor
	 * 
	 * @param message exception message
	 */
	public PlayerNotFoundException(final String message) {
		super(message);
	}
}
