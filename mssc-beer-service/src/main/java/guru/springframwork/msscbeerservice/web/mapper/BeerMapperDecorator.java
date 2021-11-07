package guru.springframwork.msscbeerservice.web.mapper;

import guru.springframwork.msscbeerservice.web.domain.Beer;
import guru.springframwork.msscbeerservice.web.model.BeerDto;
import guru.springframwork.msscbeerservice.web.services.inventory.BeerInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//@Component
public abstract class BeerMapperDecorator implements BeerMapper{

    private BeerInventoryService beerInventoryService;

    private BeerMapper beerMapper;

    @Autowired
    public void setBeerInventoryService(BeerInventoryService beerInventoryService) {
        System.out.println(">>>>>>>>>>>>>>>>>>beerInventoryService"+beerInventoryService);
        this.beerInventoryService = beerInventoryService;
    }

    @Autowired
    public void setMapper(BeerMapper mapper) {
        System.out.println(">>>>>>>>>>>>>>>>>>mapper"+mapper);
        this.beerMapper = mapper;
    }


    @Override
    public BeerDto beerToBeerDto(Beer beer) {
        BeerDto beerDto = beerMapper.beerToBeerDto(beer);

        System.out.println(">>>>>>>>>>>>>>>>>>beerDto"+beerDto.toString());
        beerDto.setQuantityOnHand(beerInventoryService.getOnHandInventory(beerDto.getId()));
        System.out.println(beerDto.toString());
        return beerDto;
    }

    @Override
    public Beer beerDtoToBeer(BeerDto beerDto) {
        return beerMapper.beerDtoToBeer(beerDto);
    }
}
