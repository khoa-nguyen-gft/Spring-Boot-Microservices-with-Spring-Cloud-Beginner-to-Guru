package guru.springframwork.mssc.brewery.web.controller;

import guru.springframwork.mssc.brewery.services.BeerService;
import guru.springframwork.mssc.brewery.web.model.BeerDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;
import java.util.List;


@Validated
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeer(@NotNull @PathVariable("beerId")UUID beerId){
        return new ResponseEntity<BeerDto>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addBeer(@Valid @RequestBody BeerDto beerDto){
        BeerDto result = beerService.saveBeer(beerDto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Location","/api/v1/beer/"+result.getId());
        httpHeaders.set("beer-id", result.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeer(@PathVariable("beerId") UUID beerId, @Valid @RequestBody BeerDto beerDto){
        BeerDto response = beerService.updateBeer(beerId, beerDto);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId){
        beerService.deleteBeer(beerId);
    }


}
