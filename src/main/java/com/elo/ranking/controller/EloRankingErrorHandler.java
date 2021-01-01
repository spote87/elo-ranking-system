package com.elo.ranking.controller;

import com.elo.ranking.exception.EloRankingSystemException;
import com.elo.ranking.exception.InvalidPlayerNameException;
import com.elo.ranking.exception.InvalidSortingStrategy;
import com.elo.ranking.exception.PlayerNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * This is exception handler for Elo Ranking application. It contains Exception
 * handlers for different exception.
 * 
 * @author Shivaji Pote
 */
@ControllerAdvice
@Log4j2
public class EloRankingErrorHandler {

	/**
	 * Exception handler for {@link PlayerNotFoundException}.
	 * 
	 * @param exception {@code PlayerNotFoundException} instance
	 * @return {@link ResponseEntity} containing http status and error message
	 */
	@ExceptionHandler(PlayerNotFoundException.class)
	public ResponseEntity<String> handlePlayerNotFoundException(final PlayerNotFoundException exception) {
		log.error(exception.getMessage(), exception);
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

	/**
	 * Exception handler for {@link InvalidPlayerNameException} and
	 * {@link InvalidSortingStrategy}.
	 * 
	 * @param exception {@link EloRankingSystemException} instance
	 * @return {@link ResponseEntity} containing http status and error message
	 */
	@ExceptionHandler({ InvalidPlayerNameException.class, InvalidSortingStrategy.class })
	public ResponseEntity<String> handleInvalidPlayerException(final EloRankingSystemException exception) {
		log.error(exception.getMessage(), exception);
		return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
	}

}
