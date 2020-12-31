package com.elo.ranking.dao;

import com.elo.ranking.model.Match;
import com.elo.ranking.reader.EloFileReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.List;

import static com.elo.ranking.utils.TestUtils.readResourceAsStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote
 */
@SpringBootTest
public class MatchesFileDataReaderTest {

    //@MockBean
    private final EloFileReader eloFileReader = Mockito.mock(EloFileReader.class);

    //@Autowired
    @InjectMocks
    private MatchesFileDataReader dataReader;// = new MatchesFileDataReader(fileReader);

    @BeforeEach
    public void setup() {
        //MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(dataReader, "dataFile", "matches/matches.txt");
        ReflectionTestUtils.setField(dataReader, "separator", " ");

    }

    @Test
    public void testRead_ReadsOnlyValidMatchesDataFromFile() throws IOException {
        when(eloFileReader.readResourceAsInputStream(anyString())).thenReturn(readResourceAsStream("matches/matches.txt"));
        final List<Match> matches = dataReader.read();
        assertNotNull(matches);
        assertEquals(4, matches.size());
    }

    @Test
    public void testRead_ReturnsEmptyListWhenReaderFailsToReadMatchesResource() throws IOException {
        when(eloFileReader.readResourceAsInputStream(anyString())).thenThrow(IOException.class);
        final List<Match> matches = dataReader.read();
        assertNotNull(matches);
        assertTrue(matches.isEmpty());
    }
}
