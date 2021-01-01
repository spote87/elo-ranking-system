package com.elo.ranking.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Shivaji Pote
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerScoreCard {

    public PlayerScoreCard(final Player player, final Integer score, final Integer rank) {
        this.player = player;
        this.score = score;
        this.rank = rank;
    }

    private Player player;

    private Integer score;

    private Integer rank;

    private Integer wins;

    private Integer losses;

}
