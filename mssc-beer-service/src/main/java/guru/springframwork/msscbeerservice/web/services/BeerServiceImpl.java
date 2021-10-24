package guru.springframwork.msscbeerservice.web.services;

import guru.springframwork.msscbeerservice.web.domain.Beer;
import guru.springframwork.msscbeerservice.web.mapper.BeerMapper;
import guru.springframwork.msscbeerservice.web.model.BeerDto;
import guru.springframwork.msscbeerservice.web.respositories.BeerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    private BeerMapper beerMapper;

    @Override
    public BeerDto getBeerById(UUID beerId) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(BeerNotFoundException::new);
        return beerMapper.beerToBeerDto(beer);
    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
        Beer beer = beerMapper.beerDtoToBeer(beerDto);
        Beer result = beerRepository.save(beer);
        return beerMapper.beerToBeerDto(result);
    }

    @Override
    public BeerDto updateBeerById(UUID beerId, BeerDto beerDto) {
        Beer beer = beerRepository.findById(beerId).orElseThrow(BeerNotFoundException::new);

        beer.setBeerName(beerDto.getBeerName());
        beer.setBeerStyle(beerDto.getBeerStyle());
        beer.setPrice(beerDto.getPrice());
        beer.setUpc(beerDto.getUpc());

        return beerMapper.beerToBeerDto(beer);

    }
}
