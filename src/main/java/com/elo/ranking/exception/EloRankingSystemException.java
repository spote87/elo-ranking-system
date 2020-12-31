package com.elo.ranking.exception;

import lombok.Getter;

/**
 * @author Shivaji Pote
 */
public class EloRankingSystemException extends Exception {

    private static final long serialVersionUID = -4646915854073154406L;

    @Getter
    private final String message;

    public EloRankingSystemException(final String message) {
        this.message=message;
    }


}
