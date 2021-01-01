package com.elo.ranking.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * @author Shivaji Pote (C62183)
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerReport {

    private Player player;

    /**
     * Map of player and result of the match
     */
    private List<MatchResult> matchResults;

    @Setter
    @Getter
    public static class MatchResult {

        private Player opposition;

        private String result;
    }
}
