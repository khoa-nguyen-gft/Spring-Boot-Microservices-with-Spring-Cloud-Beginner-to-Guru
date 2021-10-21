package guru.springframwork.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframwork.msscbeerservice.web.model.BeerDto;
import guru.springframwork.msscbeerservice.web.model.BeerStyleEnum;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.UUID;

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
        String beerDtoStr = mapper.writeValueAsString(getValidBeanDto());
        mockMvc.perform(MockMvcRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoStr)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @SneakyThrows
    void updateBeerById() {
        String content = mapper.writeValueAsString(getValidBeanDto());
        mockMvc.perform(MockMvcRequestBuilders.put(URI + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    public BeerDto getValidBeanDto(){
        return BeerDto.builder()
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .upc(3370100000000001L)
                .price(new BigDecimal("12.96"))
                .quantityOnHand(20)
                .build();
    }
}