package guru.springframwork.msscbeerservice.bootstrap;

import guru.springframwork.msscbeerservice.web.domain.Beer;
import guru.springframwork.msscbeerservice.web.model.BeerStyleEnum;
import guru.springframwork.msscbeerservice.web.respositories.BeerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class BeerLoader implements CommandLineRunner {

    public static final UUID BEER_1_UUID = UUID.fromString("0a818933-087d-47f2-ad83-2f986ed087eb");
    public static final UUID BEER_2_UUID = UUID.fromString("a712d914-61ea-4623-8bd0-32c0f6545bfd");
    public static final UUID BEER_3_UUID = UUID.fromString("026cc3c8-3a0c-4083-a05b-e908048c1b08");

    public static final String BEER_1_UPC = "0631234200036";
    public static final String BEER_2_UPC = "0631234300019";
    public static final String BEER_3_UPC = "0083783375213";

    private BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if(beerRepository.count() == 0){
            beerRepository.save(Beer.builder()
                .id(BEER_1_UUID)
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .quantityToBrew(200)
                .minOnHand(12)
                .upc(Long.valueOf(BEER_1_UPC))
                .price(new BigDecimal("12.96"))
                .build());

            beerRepository.save(Beer.builder()
                    .id(BEER_2_UUID)
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(Long.valueOf(BEER_2_UPC))
                    .price(new BigDecimal("11.96"))
                    .build());


            beerRepository.save(Beer.builder()
                    .id(BEER_3_UUID)
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyleEnum.PALE_ALE)
                    .quantityToBrew(200)
                    .minOnHand(12)
                    .upc(Long.valueOf(BEER_3_UPC))
                    .price(new BigDecimal("11.96"))
                    .build());
        }

        System.out.println(">>>>>>>>>>BeerLoader: " + beerRepository.count());
    }
}
