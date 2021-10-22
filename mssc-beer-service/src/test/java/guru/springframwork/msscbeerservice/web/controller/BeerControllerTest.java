package guru.springframwork.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframwork.msscbeerservice.web.model.BeerDto;
import guru.springframwork.msscbeerservice.web.model.BeerStyleEnum;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
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
                RestDocumentationRequestBuilders.get(URI +"{beerId}", UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .param("isCold", "true")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("/v1/beer",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        RequestDocumentation.requestParameters(
                                RequestDocumentation.parameterWithName("isCold").description("is Beer Cold Query param")
                        ),
                        PayloadDocumentation.responseFields(
                                fieldWithPath("id").description("Id of Beer"),
                                fieldWithPath("version").description("Version number"),
                                fieldWithPath("createDate").description("Date create"),
                                fieldWithPath("lastModifiedDate").description("Date updated"),
                                fieldWithPath("beerName").description("Name of Beer"),
                                fieldWithPath("beerStyle").description("Beer Style"),
                                fieldWithPath("upc").description("UPC of Beer"),
                                fieldWithPath("price").description("Price"),
                                fieldWithPath("quantityOnHand").description("quantity on Hold")
                        )


                    )
                );
    }

    @Test
    @SneakyThrows
    void saveNewBeer() {
        String beerDtoStr = mapper.writeValueAsString(getValidBeanDto());
        mockMvc.perform(RestDocumentationRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoStr)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(MockMvcRestDocumentation.document("/v1/beer",
                PayloadDocumentation.requestFields(
                        fieldWithPath("id").ignored(),
                        fieldWithPath("version").ignored(),
                        fieldWithPath("createDate").ignored(),
                        fieldWithPath("lastModifiedDate").ignored(),
                        fieldWithPath("beerName").description("Name of Beer"),
                        fieldWithPath("beerStyle").description("Beer Style"),
                        fieldWithPath("upc").description("UPC of Beer").attributes(),
                        fieldWithPath("price").description("Price"),
                        fieldWithPath("quantityOnHand").ignored()
                )
                )
        )
        ;
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