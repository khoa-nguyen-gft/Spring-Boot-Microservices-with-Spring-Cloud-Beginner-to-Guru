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
                .beerName("Mango Bobs")
                .beerStyle(BeerStyleEnum.IPA)
                .quantityToBrew(200)
                .minOnHand(12)
                .upc(3370100000000001L)
                .price(new BigDecimal("12.96"))
                .build());

//            beerRepository.save(Beer.builder()
//                    .id(UUID.randomUUID())
//                    .beerName("Galaxy Cat")
//                    .beerStyle(BeerStyleEnum.PALE_ALE)
//                    .quantityToBrew(200)
//                    .minOnHand(12)
//                    .upc(3370100000000001L)
//                    .price(new BigDecimal("11.96"))
//                    .build());
        }

        System.out.println(">>>>>>>>>>BeerLoader: " + beerRepository.count());
    }
}
