package guru.springframwork.msscbeerservice.web.controller;

import guru.springframwork.msscbeerservice.web.model.BeerDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@RequestMapping("/api/v1/beer")
@RestController
@Validated
public class BeerController {

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeerById(@NotNull @PathVariable("beerId") UUID beerId) {

        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>UUID: " + beerId);
        //TODO
        return new ResponseEntity<BeerDto>(BeerDto.builder().build(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@Valid @RequestBody BeerDto beerDto){

        //TODO
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping ("/{beerId}")
    public ResponseEntity updateBeerById(@NotNull @PathVariable("beerId") UUID beerId,@Valid @RequestBody BeerDto beerDto){
        //TODO
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
