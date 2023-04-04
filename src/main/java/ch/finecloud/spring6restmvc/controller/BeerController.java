package ch.finecloud.spring6restmvc.controller;

import ch.finecloud.spring6restmvc.model.Beer;
import ch.finecloud.spring6restmvc.services.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Controller
public class BeerController {
    private final BeerService beerService;

    public Beer getBeerById(UUID id) {

        log.debug("getBeerById was called with id: " + id + ", in Controller");

        return beerService.getBeerById(id);
    }

}
