package com.elo.ranking.controller;

import com.elo.ranking.EloRankingApplication;
import com.elo.ranking.exception.PlayerNotFoundException;
import com.elo.ranking.model.PlayerReport;
import com.elo.ranking.model.PlayerScoreCard;
import com.elo.ranking.strategy.RankingContext;
import com.elo.ranking.strategy.ReportGenerationStrategy;
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

import java.util.ArrayList;
import java.util.List;

import static com.elo.ranking.utils.TestUtils.*;
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

    @MockBean
    private ReportGenerationStrategy reportGenerationStrategy;

    private final ObjectMapper mapper = new ObjectMapper();

    @Test
    void showRanking_RespondsWithRankingsAndStatus200() throws Exception {
        when(rankingContext.executeStrategy()).thenReturn(mockedRankingData(5));
        final List<PlayerScoreCard> response = mapper.readValue(readFile("user-ranking-response.json"), List.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/rankings/").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(response)));
    }

    @Test
    void showRanking_RespondsRankingOfSinglePlayerAndtatus200() throws Exception {
        when(rankingContext.executeStrategy()).thenReturn(mockedRankingData(1));
        final List<PlayerScoreCard> response = mapper.readValue(readFile("user-ranking-response.json"), List.class);
        mockMvc.perform(MockMvcRequestBuilders.get("/rankings/").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("[{\"player\":{\"id\":1,\"name\":\"Test Player1\"},\"score\":1,\"rank\":1}]"));
    }

    @Test
    void showRanking_ThrowsProperErrorMessageWhenPlayerNotFound() throws Exception {
        when(rankingContext.executeStrategy()).thenThrow(new PlayerNotFoundException("Player xxx not found in system"));
        mockMvc.perform(MockMvcRequestBuilders.get("/rankings/").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Player xxx not found in system"));
    }

    @Test
    void getReport_ReturnsPlayerReportAndStatus200() throws Exception {
        final PlayerReport response = mockedReportData();
        when(rankingContext.executeStrategy()).thenReturn(response);
        mockMvc.perform(MockMvcRequestBuilders.get("/report/Test Player").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(asJsonString(response)));
    }

    @Test
    void getReport_ThrowsProperErrorMessageWhenPlayerNotFound() throws Exception {
        when(rankingContext.executeStrategy()).thenThrow(new PlayerNotFoundException("Player xxx not found in system"));
        mockMvc.perform(MockMvcRequestBuilders.get("/report/xxx").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andExpect(MockMvcResultMatchers.content().string("Player xxx not found in system"));
    }

    private PlayerReport mockedReportData() {
        final PlayerReport report = new PlayerReport();
        report.setPlayer(mockedPlayer(1));
        final List<PlayerReport.MatchResult> matchResults = new ArrayList<>();
        final PlayerReport.MatchResult result1 = new PlayerReport.MatchResult();
        result1.setOpposition(mockedPlayer(2));
        result1.setResult("Won");
        matchResults.add(result1);
        final PlayerReport.MatchResult result2 = new PlayerReport.MatchResult();
        result2.setOpposition(mockedPlayer(3));
        result2.setResult("Lost");
        matchResults.add(result2);
        report.setMatchResults(matchResults);
        return report;
    }

}