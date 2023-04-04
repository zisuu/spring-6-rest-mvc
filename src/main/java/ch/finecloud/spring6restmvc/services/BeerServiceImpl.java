package ch.finecloud.spring6restmvc.services;

import ch.finecloud.spring6restmvc.model.Beer;
import ch.finecloud.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {
    @Override
    public Beer getBeerById(UUID id) {

        log.debug("getBeerById was called with id: " + id + ", in Service");

        return Beer.builder()
                .id(id)
                .version(1)
                .beerName("Moudi Bier")
                .beerStyle(BeerStyle.NEIPA)
                .upc("123456")
                .quantityOnHand(100)
                .price(new BigDecimal(12.99))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }
}
