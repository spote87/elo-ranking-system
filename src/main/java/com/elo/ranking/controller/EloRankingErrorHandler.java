package com.elo.ranking.controller;

import com.elo.ranking.exception.PlayerNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author Shivaji Pote (C62183)
 */
@ControllerAdvice
@Log4j2
public class EloRankingErrorHandler {

    @ExceptionHandler(PlayerNotFoundException.class)
    public ResponseEntity<?> handlePlayerNotFoundException(final PlayerNotFoundException exception){
        log.error(exception.getMessage(),exception);
        return new ResponseEntity<>(exception.getMessage(),HttpStatus.NOT_FOUND);
    }
}
