package it.emrah.command.processor.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
class TaskCreatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvFileSource(resources = "/rest_test.csv", delimiter = '#', numLinesToSkip = 1)
    void processTasks(String input, boolean expected) throws Exception {

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/")
                                                        .content(input)
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();


    }
}