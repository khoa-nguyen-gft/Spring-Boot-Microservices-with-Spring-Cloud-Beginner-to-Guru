package guru.springframwork.msscbeerservice.web.services;

import guru.springframwork.msscbeerservice.web.model.BeerDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public BeerDto getBeerById(UUID beerId) {
        return null;
    }

    @Override
    public void saveBeer(BeerDto beerDto) {

    }

    @Override
    public void updateBeerById(UUID beerId, BeerDto beerDto) {

    }
}
