package guru.springframwork.msscbeerservice.web.controller;

import guru.springframwork.msscbeerservice.web.model.BeerDto;
import guru.springframwork.msscbeerservice.web.services.BeerService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class BeerController {

    private final BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@NotNull @PathVariable("beerId") UUID beerId) {
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
