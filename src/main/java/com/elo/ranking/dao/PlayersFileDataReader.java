package com.elo.ranking.dao;

import com.elo.ranking.model.Player;
import com.elo.ranking.reader.EloFileReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This {@link PlayersDataReader} implementation reads players data from files.
 * 
 * @author Shivaji Pote
 */
@Log4j2
@RequiredArgsConstructor
@Repository
public class PlayersFileDataReader implements PlayersDataReader {

	@Value("${players.file.data.separator}")
	private String separator;

	@Value("${players.input.file.path}")
	private String dataFile;

	private final EloFileReader eloFileReader;

	/**
	 * This method reads all {@link Player}s data from file configured at
	 * {@code players.input.file.path}.
	 */
	@Override
	public List<Player> readAll() {
		log.debug("Reading players data from file {}", dataFile);
		try (final InputStream dataStream = eloFileReader.readResourceAsInputStream(dataFile)) {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream));
			return readData(reader);
		} catch (final IOException e) {
			log.error("Failed to readAll players data", e);
		}
		return Collections.emptyList();
	}

	/**
	 * This method reads {@link Player}s data of specified player from file
	 * configured at {@code players.input.file.path}.
	 */
	@Override
	public Player byId(final Integer playerId) {
		log.debug("Reading data for player id {}", playerId);
		final Optional<Player> playerOptional = readAll().stream().filter(player -> player.getId() == playerId)
				.findFirst();
		if (playerOptional.isPresent()) {
			return playerOptional.get();
		} else {
			return null;
		}
	}

	/**
	 * This method reads {@link Player}s data from file configured at
	 * {@code players.input.file.path}.
	 */
	@Override
	public Player byName(final String name) {
		log.debug("Reading data for player {}", name);
		final Optional<Player> playerOptional = readAll().stream()
				.filter(player -> player.getName().equalsIgnoreCase(name)).findFirst();
		if (playerOptional.isPresent()) {
			return playerOptional.get();
		} else {
			return null;
		}
	}

	private List<Player> readData(final BufferedReader reader) {
		return reader.lines().filter(StringUtils::hasText).map(line -> getMatch(separator, line))
				.filter(Objects::nonNull).collect(Collectors.toList());
	}

	private Player getMatch(final String separator, final String line) {
		final String[] data = line.split(separator);
		if (data.length >= 2) {
			final Player player = new Player();
			try {
				player.setId(Integer.parseInt(data[0]));
				player.setName(data[1]);
				return player;
			} catch (final NumberFormatException e) {
				log.error("Invalid input on line {}. Ignoring this input line", line, e);
			}
		}
		return null;
	}

}
