package com.elo.ranking.exception;

/**
 * @author Shivaji Pote (C62183)
 */
public class InvalidSortingStrategy extends EloRankingSystemException {
    private static final long serialVersionUID = 3576041409298033754L;

    public InvalidSortingStrategy(final String message) {
        super(message);
    }
}
