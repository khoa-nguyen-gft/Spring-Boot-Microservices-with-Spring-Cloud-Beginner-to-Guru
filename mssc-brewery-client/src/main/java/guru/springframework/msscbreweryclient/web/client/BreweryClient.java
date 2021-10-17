package guru.springframework.msscbreweryclient.web.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.msscbreweryclient.web.model.BeerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.UUID;

@Component
@ConfigurationProperties(value="sfg.brewery", ignoreUnknownFields = false)
public class BreweryClient {
    public final String URI_V1 = "/api/v1/beer/";

    public final RestTemplate restTemplate;

    @Autowired
    public ObjectMapper mapper;

    private String apiHost;

    public BreweryClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public void setApiHost(String apiHost) {
        this.apiHost = apiHost;
    }

    public BeerDto getBeerById(UUID uuid) throws java.io.IOException {
        String url = apiHost + URI_V1 + uuid.toString();
        BeerDto beerDto  = restTemplate.getForObject(url, BeerDto.class);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>url: " + url);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>beerDto: " + beerDto.toString());

        return beerDto;
    }

    public URI addBeer(BeerDto beerDto) {
        String url = apiHost + URI_V1;
        URI uri = restTemplate.postForLocation(url, beerDto);

        return uri;
    }

    public void updateBeer(UUID id, BeerDto beerDto) {
        String url = apiHost + URI_V1 + id;
        restTemplate.put(url, beerDto);
    }

    public void deleteBeer(UUID beerId){
        String url = apiHost + URI_V1 + beerId;
        restTemplate.delete(url);
    }
}
