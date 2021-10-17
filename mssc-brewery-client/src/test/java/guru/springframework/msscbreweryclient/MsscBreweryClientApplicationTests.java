package guru.springframework.msscbreweryclient;

import guru.springframework.msscbreweryclient.web.client.BreweryClient;
import guru.springframework.msscbreweryclient.web.model.BeerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.UUID;

@SpringBootTest
public class MsscBreweryClientApplicationTests {

    @Autowired
    BreweryClient client;

    @Test
    void getBeerById() throws IOException {
        BeerDto beer = client.getBeerById(UUID.randomUUID());
        Assertions.assertNotNull(beer);
    }

    @Test
    public void contextLoads() {
    }

}
