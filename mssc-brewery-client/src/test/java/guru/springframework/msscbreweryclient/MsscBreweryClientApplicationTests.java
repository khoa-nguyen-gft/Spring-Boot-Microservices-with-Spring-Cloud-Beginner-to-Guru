package guru.springframework.msscbreweryclient;

import guru.springframework.msscbreweryclient.web.client.BreweryClient;
import guru.springframework.msscbreweryclient.web.model.BeerDto;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@SpringBootTest
public class MsscBreweryClientApplicationTests {

    @Autowired
    BreweryClient client;

    @Test
    @SneakyThrows
    void getBeerById()  {
        BeerDto beer = client.getBeerById(UUID.randomUUID());
        Assertions.assertNotNull(beer.getId());
    }

    @Test
    void addBeer(){
        BeerDto beerDto = BeerDto.builder().id(UUID.randomUUID()).beerName("Galaxy Cat").beerStyle("Pale Ale").build();
        URI uri = client.addBeer(beerDto);

        Assertions.assertNotNull(uri);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>URI" + uri.toString());
    }

    @Test
    void updateBeer() {
        BeerDto beerDto = BeerDto.builder().id(UUID.randomUUID()).beerName("Galaxy Cat").beerStyle("Pale Ale").build();
        client.updateBeer(beerDto.getId(),beerDto);
    }

    @Test
    void deleteBeer(){
        client.deleteBeer(UUID.randomUUID());
    }

    @Test
    public void contextLoads() {
    }

}
