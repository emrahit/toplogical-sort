package it.emrah.command.processor.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import it.emrah.command.processor.model.rest.TaskResponse;
import it.emrah.command.processor.util.JsonArgumentConverter;

@SpringBootTest
@AutoConfigureMockMvc
class TaskCreatorControllerTest {

    private ObjectMapper objectMapper = new ObjectMapper();

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


    @ParameterizedTest
    @CsvFileSource(resources = "/rest_test_response.csv", delimiter = '#', numLinesToSkip = 1)
    public void validateMessageResponse(String input, int expected,
                                        @ConvertWith(JsonArgumentConverter.class) TaskResponse[] expectedResponse)
            throws Exception {
        this.mockMvc.perform(post("/").content(input)
                                     .header("Content-Type", "application/json")).andDo(print())
                .andExpect(x -> {
                    TaskResponse[] responseList =
                            objectMapper.readValue(x.getResponse().getContentAsString(), TaskResponse[].class);
                    for (int i = 0; i < responseList.length; i++) {
                        assertEquals(expectedResponse[i], responseList[i]);
                    }
                    assertEquals(expected, x.getResponse().getStatus());
                });
    }

    @ParameterizedTest
    @CsvFileSource(resources = {"/rest_test_response.csv"}, delimiter = '#', numLinesToSkip = 1)
    public void validateMessageResponseForBash(String input, int expected)
            throws Exception {
        String file = new String(
                getClass().getClassLoader().getResourceAsStream("rest_test_response_bash.bash").readAllBytes());

        this.mockMvc.perform(post("/bash").content(input)
                                     .header("Content-Type", "application/json")).andDo(print())
                .andExpect(x -> {
                    String response = x.getResponse().getContentAsString();
                    assertEquals(file, response);
                    assertEquals(expected, x.getResponse().getStatus());
                });
    }
}