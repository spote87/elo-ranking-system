package com.elo.ranking.controller;

import com.elo.ranking.EloRankingApplication;
import com.elo.ranking.exception.InvalidSortingStrategy;
import com.elo.ranking.factory.SortingFactory;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.strategy.RankingContext;
import com.elo.ranking.utils.TestUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static com.elo.ranking.utils.TestUtils.asJsonString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * @author Shivaji Pote
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = EloRankingApplication.class)
@AutoConfigureMockMvc
class EloSortingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RankingContext rankingContext;

    @MockBean
    private SortingFactory sortingFactory;

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    void listPlayers_ReturnsPlayersSortedByPlayerId() throws Exception {
        final List<PlayerScoreCard> response = mockedScoreCardData();
        when(rankingContext.executeStrategy()).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.get("/players").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(response)));
    }

    @Test
    void listPlayers_ReturnsPlayersSortedByScore() throws Exception {
        final List<PlayerScoreCard> response = mockedScoreCardData();
        when(rankingContext.executeStrategy()).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.get("/players?sortBy=score&order=asc").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(response)));
    }

    @Test
    void listPlayers_ThrowsProperErrorMessageWhenInvalidSortingFieldMentioned() throws Exception {
        doThrow(new InvalidSortingStrategy("Unexpected value: aa")).when(sortingFactory).getSortingStrategy(Mockito.anyString(), Mockito.anyString());
        mockMvc.perform(MockMvcRequestBuilders.get("/players?sortBy=aa&order=asc").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.content().string("Unexpected value: aa"));
    }

    private List<PlayerScoreCard> mockedScoreCardData() {
        final List<PlayerScoreCard> scoreCards = new ArrayList<>();
        scoreCards.add(new PlayerScoreCard(TestUtils.mockedPlayer(1), 5, 1, 5, 0));
        scoreCards.add(new PlayerScoreCard(TestUtils.mockedPlayer(2), 5, 2, 2, 3));
        return scoreCards;
    }
}