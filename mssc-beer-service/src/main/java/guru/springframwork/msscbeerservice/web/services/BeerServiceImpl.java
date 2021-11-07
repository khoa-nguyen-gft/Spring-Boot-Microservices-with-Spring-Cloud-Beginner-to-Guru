package guru.springframwork.msscbeerservice.web.services;

import guru.springframwork.msscbeerservice.web.domain.Beer;
import guru.springframwork.msscbeerservice.web.mapper.BeerMapper;
import guru.springframwork.msscbeerservice.web.model.BeerDto;
import guru.springframwork.msscbeerservice.web.model.BeerPagedList;
import guru.springframwork.msscbeerservice.web.model.BeerStyleEnum;
import guru.springframwork.msscbeerservice.web.respositories.BeerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    /*@Autowired
    public BeerServiceImpl(BeerRepository beerRepository, BeerMapper beerMapper) {
        this.beerRepository = beerRepository;
        this.beerMapper = beerMapper;
    }*/

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

    @Override
    public BeerPagedList listBeers(String beerName, BeerStyleEnum beerStyle, PageRequest pageRequest) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>beerName" + beerName);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>beerStyle" + beerStyle.toString());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>pageRequest" + pageRequest.toString());

        Page<Beer> beerPage = null;

        if(!StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
           beerPage = beerRepository.findAllByBeerNameAndBeerStyle(beerName, beerStyle, pageRequest);

        } else if(!StringUtils.isEmpty(beerName) && StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerName(beerName, pageRequest);

        } else if(StringUtils.isEmpty(beerName) && !StringUtils.isEmpty(beerStyle)){
            beerPage = beerRepository.findAllByBeerStyle(beerStyle, pageRequest);

        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }


        List<Beer> collect = beerPage.get().collect(Collectors.toList());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>beerPage" + collect);
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>beerMapper" + beerMapper);


        List<BeerDto> beerDto = beerPage.get().map(beerMapper::beerToBeerDto).collect(Collectors.toList());


        return new BeerPagedList(beerDto,
                PageRequest.of(beerPage.getPageable().getPageNumber(),
                            beerPage.getPageable().getPageSize()
                        ),
                beerDto.size());
    }
}
