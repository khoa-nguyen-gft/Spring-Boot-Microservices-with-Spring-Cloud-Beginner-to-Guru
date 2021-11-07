package guru.springframwork.msscbeerservice.web.controller;

import guru.springframwork.msscbeerservice.web.model.BeerDto;
import guru.springframwork.msscbeerservice.web.model.BeerPagedList;
import guru.springframwork.msscbeerservice.web.model.BeerStyleEnum;
import guru.springframwork.msscbeerservice.web.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
@Validated
//@AllArgsConstructor
@RequiredArgsConstructor
public class BeerController {

    private static final Integer DEFAULT_PAGE_NUMBER = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 25;

    private final BeerService beerService;

    @GetMapping
    public ResponseEntity<BeerPagedList> listBeers(@RequestParam(value="pageNumber") Integer pageNumber,
                                                   @RequestParam(value="pageSize")   Integer pageSize,
                                                   @RequestParam(value ="beerName")  String beerName,
                                                   @RequestParam(value="beerStyle")  BeerStyleEnum beerStyle
                                                   ){
        if(pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }

        if(pageSize == null || pageSize < 0){
            pageSize = DEFAULT_PAGE_SIZE;
        }

        BeerPagedList beerList = beerService.listBeers(beerName, beerStyle, PageRequest.of(pageNumber, pageSize));



        return new ResponseEntity<>(beerList, HttpStatus.OK);
    }


    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@NotNull @PathVariable("beerId") UUID beerId) {
        BeerDto result = beerService.getBeerById(beerId);

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>BeerDto: " + result.toString());
        return new ResponseEntity<BeerDto>(beerService.getBeerById(beerId),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<BeerDto> saveNewBeer(@Valid @RequestBody BeerDto beerDto){
        BeerDto result = beerService.saveBeer(beerDto);
        return new ResponseEntity<BeerDto>(result, HttpStatus.CREATED);
    }

    @PutMapping ("/{beerId}")
    public ResponseEntity<BeerDto> updateBeerById(@NotNull @PathVariable("beerId") UUID beerId,@Valid @RequestBody BeerDto beerDto){
        return new ResponseEntity<BeerDto>(beerService.updateBeerById(beerId, beerDto), HttpStatus.NO_CONTENT);
    }
}
