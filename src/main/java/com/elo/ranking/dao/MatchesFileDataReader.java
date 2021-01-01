package com.elo.ranking.dao;

import com.elo.ranking.model.Match;
import com.elo.ranking.reader.EloFileReader;
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
import java.util.stream.Collectors;

/**
 * This {@link MatchesDataReader} implementation reads matches data from files.
 *
 * @author Shivaji Pote
 */
@Log4j2
@Repository
public class MatchesFileDataReader implements MatchesDataReader {

	@Value("${matches.file.data.separator}")
	private String separator;

	@Value("${matches.input.file.path}")
	private String dataFile;

	private final EloFileReader eloFileReader;

	/**
	 * {@link MatchesFileDataReader} constructor.
	 *
	 * @param eloFileReader file reader instance
	 */
	public MatchesFileDataReader(final EloFileReader eloFileReader) {
		this.eloFileReader = eloFileReader;
	}

	/**
	 * This method reads all matches data from matches datasource file configured at
	 * {@code matches.input.file.path}.
	 */
	@Override
	public List<Match> read() {
		log.debug("Reading matches data from file {}", dataFile);
		try (final InputStream dataStream = eloFileReader.readResourceAsInputStream(dataFile)) {
			final BufferedReader reader = new BufferedReader(new InputStreamReader(dataStream));
			return readData(reader);
		} catch (final IOException e) {
			log.error("Failed to readAll matches data", e);
		}
		return Collections.emptyList();
	}

	/**
	 * This method reads matches data for specified player from matches datasource
	 * file configured at {@code matches.input.file.path}.
	 */
	@Override
	public List<Match> getMatches(final int playerId) {
		return read().stream().filter(match -> match.getWinner() == playerId || match.getLoser() == playerId)
				.collect(Collectors.toList());
	}

	private List<Match> readData(final BufferedReader reader) {
		return reader.lines().filter(StringUtils::hasText).map(line -> getMatch(separator, line))
				.filter(Objects::nonNull).collect(Collectors.toList());
	}

	private Match getMatch(final String separator, final String line) {
		final String[] data = line.split(separator);
		if (data.length >= 2) {
			final Match match = new Match();
			try {
				match.setWinner(Integer.parseInt(data[0]));
				match.setLoser(Integer.parseInt(data[1]));
				return match;
			} catch (final NumberFormatException e) {
				log.error("Invalid input on line {}. Ignoring this input line", line, e);
			}
		}
		return null;
	}

}
