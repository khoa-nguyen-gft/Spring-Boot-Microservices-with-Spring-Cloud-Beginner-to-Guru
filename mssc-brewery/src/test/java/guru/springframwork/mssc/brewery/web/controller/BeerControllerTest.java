package guru.springframwork.mssc.brewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframwork.mssc.brewery.services.BeerService;
import guru.springframwork.mssc.brewery.web.model.BeerDto;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    BeerDto validBeer;

    private final String URI = "/api/v1/beer/";

    @Before
    public void setup(){
        validBeer = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Beer1")
                .beerStyle("PALE_ALE")
                .upc(123456789L)
                .build();
    }

    @Test
    @SneakyThrows
    public void getBeer(){
        BDDMockito.given(beerService.getBeerById(ArgumentMatchers.any(UUID.class)))
                .willReturn(validBeer);

        mockMvc.perform(MockMvcRequestBuilders.get(URI + validBeer.getId().toString())
                     .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", CoreMatchers.is(validBeer.getId().toString())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.beerName", CoreMatchers.is(validBeer.getBeerName().toString())));
        ;
    }

    @Test
    @SneakyThrows
    public void handleAddBeer() {
        BeerDto beerDto = validBeer;
        beerDto.setId(null);

        BeerDto saveBeer = BeerDto.builder().id(UUID.randomUUID()).beerName("new beer").build();

        BDDMockito.given(beerService.saveBeer(ArgumentMatchers.any())).willReturn(saveBeer);

        mockMvc.perform(MockMvcRequestBuilders
                    .post(URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content( objectMapper.writeValueAsString(beerDto)))
            .andExpect(status().isCreated())
        ;
    }

    @Test
    @SneakyThrows
    public void handleUpdate(){
        String validStr = objectMapper.writeValueAsString(validBeer);

        mockMvc.perform(MockMvcRequestBuilders.put(URI+validBeer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(validStr))
                .andExpect(status().isNoContent());

        BDDMockito.then(beerService).should().updateBeer(ArgumentMatchers.any(),ArgumentMatchers.any());


    }
}
