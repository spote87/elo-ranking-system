package com.elo.ranking.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Model class for players data.
 * 
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		} else if (obj == null) {
			return false;
		} else if (obj instanceof Player) {
			Player player = (Player) obj;
			return this.id == player.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(this);
	}
}
