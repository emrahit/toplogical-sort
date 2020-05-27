package it.emrah.command.processor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class TaskCreatorControllerTest {



    @Autowired
    private MockMvc mockMvc;

    @ParameterizedTest
    @CsvFileSource(resources = "/rest_test.csv", delimiter = '#', numLinesToSkip = 1)
    public void shouldReturnDefaultMessage(String url, String input, int expected) throws Exception {
        this.mockMvc.perform(post(url).content(input)
                                     .header("Content-Type", "application/json")).andDo(print())
                .andExpect(x -> {
                    assertEquals(expected, x.getResponse().getStatus());
                });
    }
}