package guru.springframwork.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframwork.msscbeerservice.web.model.BeerDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static final String URI = "/api/v1/beer/";

    @Test
    @SneakyThrows
    void getBeerById() {
        mockMvc.perform(
                MockMvcRequestBuilders.get(URI + UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(MockMvcResultMatchers.status().isOk());
        ;
    }

    @Test
    @SneakyThrows
    void saveNewBeer() {
        String beerDtoStr = mapper.writeValueAsString(BeerDto.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoStr)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @SneakyThrows
    void updateBeerById() {
        String content = mapper.writeValueAsString(BeerDto.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.put(URI + UUID.randomUUID().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                        .accept(MediaType.APPLICATION_JSON)
                    )
            .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}