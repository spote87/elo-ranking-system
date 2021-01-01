package com.elo.ranking.exception;

/**
 * Exception class for invalid player name.
 * 
 * @author Shivaji Pote
 */
public class InvalidPlayerNameException extends EloRankingSystemException {

	private static final long serialVersionUID = -3404794500682360179L;

	/**
	 * {@link InvalidPlayerNameException} constructor
	 * 
	 * @param message exception message
	 */
	public InvalidPlayerNameException(final String message) {
		super(message);
	}
}
