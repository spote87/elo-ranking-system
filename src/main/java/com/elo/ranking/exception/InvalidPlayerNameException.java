package com.elo.ranking.exception;

/**
 * @author Shivaji Pote (C62183)
 */
public class InvalidPlayerNameException extends EloRankingSystemException {

    private static final long serialVersionUID = -3404794500682360179L;

    public InvalidPlayerNameException(final String message) {
        super(message);
    }
}
