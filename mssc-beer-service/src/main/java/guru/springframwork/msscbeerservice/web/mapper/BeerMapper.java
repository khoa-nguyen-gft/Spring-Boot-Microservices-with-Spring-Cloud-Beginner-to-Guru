package guru.springframwork.msscbeerservice.web.mapper;

import guru.springframwork.msscbeerservice.web.domain.Beer;
import guru.springframwork.msscbeerservice.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {
    BeerDto BeerToBeerDto(Beer beer);
    Beer BeerToBeerDto(BeerDto beerDto);
}
