package com.elo.ranking.reader;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shivaji Pote
 */
@Component
public class EloFileReader {

    /**
     * This method reads resource as input stream.
     *
     * @param resource resource to be readAll
     * @return resource input stream
     * @throws IOException if fails to readAll resource
     */
    public InputStream readResourceAsInputStream(final String resource) throws IOException {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resource);
        if (inputStream == null) {
            throw new IOException("Failed to readAll resource " + resource);
        }
        return inputStream;
    }
}
