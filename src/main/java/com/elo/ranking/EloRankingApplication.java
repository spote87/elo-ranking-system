package com.elo.ranking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application launcher class.
 *
 * @author Shivaji Pote
 */
@SpringBootApplication
public class EloRankingApplication {

    /**
     * Main method which will be used by Boot to lauch application.
     *
     * @param args command line arguments
     */
    public static void main(final String[] args) {
        SpringApplication.run(EloRankingApplication.class, args);
    }
}
