package guru.springframwork.msscbeerservice.web.model;


import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class BaseTest {
    BeerDto getBeerDto() {
        return BeerDto.builder()
                .beerName("Beer Name")
                .beerStyle(BeerStyleEnum.IPA)
                .createdDate(OffsetDateTime.now())
                .lastModifiedDate(OffsetDateTime.now())
                .price(new BigDecimal("12.34"))
                .upc(473_298_473_289L)
                .id(UUID.randomUUID())
                .build();
    }
}
