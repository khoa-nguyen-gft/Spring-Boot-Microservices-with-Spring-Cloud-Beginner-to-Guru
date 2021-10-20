package guru.springframwork.mssc.brewery.web.mapper;

import guru.springframwork.mssc.brewery.web.domain.Beer;
import guru.springframwork.mssc.brewery.web.model.BeerDto;
import org.mapstruct.Mapper;

@Mapper(uses = {DateMapper.class})
public interface BeerMapper {
    BeerDto beerToBeerDto(Beer beer);
    Beer beerToBeerDto(BeerDto beerDto);
}
