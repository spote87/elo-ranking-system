package com.elo.ranking.controller;

import com.elo.ranking.EloRankingApplication;
import com.elo.ranking.exception.PlayerNotFoundException;
import com.elo.ranking.model.PlayerRank;
import com.elo.ranking.model.RankingRequest;
import com.elo.ranking.strategy.RankingContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.InputStream;
import java.util.List;

import static com.elo.ranking.utils.TestUtils.mockedRankingData;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote (C62183)
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = EloRankingApplication.class)
@AutoConfigureMockMvc
class EloRankingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RankingContext rankingContext;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void showRanking_RespondsWithRankingsAndStatus200() throws Exception {
        when(rankingContext.executeStrategy(any(RankingRequest.class))).thenReturn(mockedRankingData(5));
        final List<PlayerRank> response = mapper.readValue(readFile("user-ranking-response.json"), List.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/rankings/").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(response)));
    }

    @Test
    void showRanking_RespondsRankingOfSinglePlayerAndtatus200() throws Exception {
        when(rankingContext.executeStrategy(any(RankingRequest.class))).thenReturn(mockedRankingData(1));
        final List<PlayerRank> response = mapper.readValue(readFile("user-ranking-response.json"), List.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/rankings/").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[{\"player\":{\"id\":1,\"name\":\"Test Player1\"},\"score\":1,\"rank\":1}]"));
    }

    @Test
    void showRanking_ThrowsProperErrorMessageWhenPlayerNotFound() throws Exception {
        when(rankingContext.executeStrategy(any(RankingRequest.class))).thenThrow(new PlayerNotFoundException("Player xxx not found in system"));
        mockMvc.perform(MockMvcRequestBuilders.get("/rankings/").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Player xxx not found in system"));
    }

    @Test
    void listPlayers() {
    }

    /*
     * converts a Java object into JSON representation
     */
    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads file and returns {@link InputStream} instance.
     *
     * @param fileName name of the file
     * @return {@code InputStream} instance
     */
    protected InputStream readFile(final String fileName) {
        final ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }

}