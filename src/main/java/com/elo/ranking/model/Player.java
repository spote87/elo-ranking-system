package com.elo.ranking.model;

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
public class Player implements Comparable<Player> {

    private int id;

    private String name;

    @Override
    public int compareTo(final Player player) {
        return getId() - player.getId();
    }
}
