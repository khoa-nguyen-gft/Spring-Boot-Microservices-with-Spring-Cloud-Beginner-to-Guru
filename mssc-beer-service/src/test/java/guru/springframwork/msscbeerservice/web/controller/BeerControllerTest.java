package guru.springframwork.msscbeerservice.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframwork.msscbeerservice.web.model.BeerDto;
import guru.springframwork.msscbeerservice.web.model.BeerStyleEnum;
import guru.springframwork.msscbeerservice.web.services.BeerService;
import lombok.SneakyThrows;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BeerService service;

    private static final String URI = "/api/v1/beer/";

    private ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

    @Test
    @SneakyThrows
    void getBeerById() {
        BDDMockito.given(service.getBeerById(any())).willReturn(getValidBeanDto());
        mockMvc.perform(
                RestDocumentationRequestBuilders.get(URI +"{beerId}", UUID.randomUUID().toString())
                        .accept(MediaType.APPLICATION_JSON)
                        .param("isCold", "true")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcRestDocumentation.document("v1/beer/get",
                        RequestDocumentation.pathParameters(
                                RequestDocumentation.parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        RequestDocumentation.requestParameters(
                                RequestDocumentation.parameterWithName("isCold").description("is Beer Cold Query param")
                        ),
                        PayloadDocumentation.responseFields(
                                fields.withPath("id").description("Id of Beer"),
                                fields.withPath("version").description("Version number"),
                                fields.withPath("createDate").description("Date create"),
                                fields.withPath("lastModifiedDate").description("Date updated"),
                                fields.withPath("beerName").description("Name of Beer"),
                                fields.withPath("beerStyle").description("Beer Style"),
                                fields.withPath("upc").description("UPC of Beer"),
                                fields.withPath("price").description("Price"),
                                fields.withPath("quantityOnHand").description("quantity on Hold")
                        )
                    )
                )
     ;
    }

    @Test
    @SneakyThrows
    void saveNewBeer() {
        BDDMockito.given(service.saveBeer(any())).willReturn(getValidBeanDto());
        BeerDto params = getValidBeanDto();
        params.setId(null);
        params.setVersion(null);
        params.setCreateDate(null);
        params.setLastModifiedDate(null);

        String beerDtoStr = mapper.writeValueAsString(params);

        mockMvc.perform(RestDocumentationRequestBuilders.post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoStr)
                .accept(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isCreated())
        .andDo(MockMvcRestDocumentation.document("v1/beer/post",
                PayloadDocumentation.requestFields(
                        fields.withPath("id").ignored(),
                        fields.withPath("version").ignored(),
                        fields.withPath("createDate").ignored(),
                        fields.withPath("lastModifiedDate").ignored(),
                        fields.withPath("beerName").description("Name of Beer"),
                        fields.withPath("beerStyle").description("Beer Style"),
                        fields.withPath("upc").description("UPC of Beer").attributes(),
                        fields.withPath("price").description("Price"),
                        fields.withPath("quantityOnHand").ignored()
                )
                )
        )
        ;
    }

    @Test
    @SneakyThrows
    void updateBeerById() {
        BDDMockito.given(service.updateBeerById(any(), any())).willReturn(getValidBeanDto());

        BeerDto params = getValidBeanDto();
        params.setId(null);
        params.setVersion(null);
        params.setCreateDate(null);
        params.setLastModifiedDate(null);

        String content = mapper.writeValueAsString(params);
        mockMvc.perform(MockMvcRequestBuilders.put(URI + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    public BeerDto getValidBeanDto(){
        return BeerDto.builder()
                .id(UUID.randomUUID())
                .version(1)
                .lastModifiedDate(OffsetDateTime.now())
                .createDate(OffsetDateTime.now())
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .upc(3370100000000001L)
                .price(new BigDecimal("12.96"))
                .quantityOnHand(20)
                .build();
    }

    private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;

        private ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path){
            return fieldWithPath(path).attributes(
                    Attributes.key("constraints").value(
                            StringUtils.collectionToDelimitedString(
                                    this.constraintDescriptions.descriptionsForProperty(path),
                                    ". "
                            )
                    )
            );
        }

    }
}