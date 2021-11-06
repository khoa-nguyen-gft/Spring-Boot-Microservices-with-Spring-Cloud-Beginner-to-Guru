package guru.springframwork.msscbeerservice.web.services.inventory;

import guru.springframwork.msscbeerservice.web.model.BeerInventoryDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.IntStream;


@Slf4j
@Component
@Configuration
@ConfigurationProperties(prefix="sfg.brewery", ignoreUnknownFields = false)
public class BeerInventoryServiceImpl implements BeerInventoryService {


    private static final String INVENTORY_PATH = "/api/v1/beer/{beerId}/inventory";
    private String beerInventoryServiceHost;

    private final RestTemplate restTemplate;

    public BeerInventoryServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    @Override
    public Integer getOnHandInventory(UUID beerId) {

        log.debug("call Inventory service with UUID: " + beerId.toString());

        ResponseEntity<List<BeerInventoryDto>> exchange = restTemplate.exchange(beerInventoryServiceHost + INVENTORY_PATH,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<BeerInventoryDto>>() {},
                beerId
        );

        int sum = Objects.requireNonNull(exchange.getBody()).stream().mapToInt(BeerInventoryDto::getQuantityOnHand).sum();


        return sum;
    }

    public void setBeerInventoryServiceHost(String beerInventoryServiceHost) {
        this.beerInventoryServiceHost = beerInventoryServiceHost;
    }

    public String getBeerInventoryServiceHost() {
        return beerInventoryServiceHost;
    }
}
