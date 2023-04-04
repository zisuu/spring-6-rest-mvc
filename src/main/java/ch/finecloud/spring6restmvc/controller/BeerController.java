package ch.finecloud.spring6restmvc.controller;

import ch.finecloud.spring6restmvc.model.Beer;
import ch.finecloud.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class BeerController {
    private final BeerService beerService;

    @RequestMapping("/api/v1/beers")
    public List<Beer> listBeers() {

        log.debug("listBeers was called, in Controller");

        return beerService.listBeers();
    }

    public Beer getBeerById(UUID id) {

        log.debug("getBeerById was called with id: " + id + ", in Controller");

        return beerService.getBeerById(id);
    }

}
