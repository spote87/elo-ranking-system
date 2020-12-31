package com.elo.ranking.exception;

/**
 * @author Shivaji Pote (C62183)
 */
public class PlayerNotFoundException extends EloRankingSystemException {

    private static final long serialVersionUID = -2081504112282077965L;

    public PlayerNotFoundException(final String message) {
        super(message);
    }
}
