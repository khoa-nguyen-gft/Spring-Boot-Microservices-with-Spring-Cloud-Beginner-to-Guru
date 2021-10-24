package guru.springframwork.msscbeerservice.web.services;


import guru.springframwork.msscbeerservice.web.model.BeerDto;

import java.util.UUID;

public interface BeerService {
    BeerDto getBeerById(UUID beerId);

    void saveBeer(BeerDto beerDto);

    void updateBeerById(UUID beerId, BeerDto beerDto);
}
