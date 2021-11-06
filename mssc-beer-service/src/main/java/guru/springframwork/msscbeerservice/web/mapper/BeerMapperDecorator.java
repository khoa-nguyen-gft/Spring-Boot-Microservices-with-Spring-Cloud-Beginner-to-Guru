package guru.springframwork.msscbeerservice.web.mapper;

import guru.springframwork.msscbeerservice.web.domain.Beer;
import guru.springframwork.msscbeerservice.web.model.BeerDto;
import guru.springframwork.msscbeerservice.web.services.inventory.BeerInventoryService;

public abstract class BeerMapperDecorator implements BeerMapper{

    private BeerInventoryService beerInventoryService;

    private BeerMapper beerMapper;

    public BeerMapperDecorator(BeerInventoryService beerInventoryService, BeerMapper beerMapper) {
        this.beerInventoryService = beerInventoryService;
        this.beerMapper = beerMapper;
    }

    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        BeerDto beerDto = beerMapper.beerToBeerDto(beer);
        beerDto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beerDto.getId()));
        System.out.println(beerDto.toString());
        return beerDto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return beerMapper.beerDtoToBeer(beerDto);
    }
}
