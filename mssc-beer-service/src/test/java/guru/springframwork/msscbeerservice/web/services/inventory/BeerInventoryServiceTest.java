package guru.springframwork.msscbeerservice.web.services.inventory;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@Disabled
@SpringBootTest
class BeerInventoryServiceTest {

    @Autowired
    private BeerInventoryService beerInventoryService;

    @Test
    void getOnHandInventory() {
        Integer inventory = beerInventoryService.getOnHandInventory(UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb"));

        System.out.println("inventory: " + inventory);
    }
}