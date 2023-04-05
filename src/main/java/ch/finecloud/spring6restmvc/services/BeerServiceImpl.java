package ch.finecloud.spring6restmvc.services;

import ch.finecloud.spring6restmvc.model.Beer;
import ch.finecloud.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, Beer> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        Beer beer1 = Beer.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Moudi Bier")
                .beerStyle(BeerStyle.NEIPA)
                .upc("123456")
                .price(new BigDecimal(12.99))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer2 = Beer.builder()
                .id(UUID.randomUUID())
                .version(2)
                .beerName("Moudi Bier")
                .beerStyle(BeerStyle.PILSNER)
                .upc("9567")
                .price(new BigDecimal(10.50))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        Beer beer3 = Beer.builder()
                .id(UUID.randomUUID())
                .version(3)
                .beerName("Moudi Bier")
                .beerStyle(BeerStyle.ALE)
                .upc("45452")
                .price(new BigDecimal(9.99))
                .quantityOnHand(100)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();

        beerMap.put(beer1.getId(), beer1);
        beerMap.put(beer2.getId(), beer2);
        beerMap.put(beer3.getId(), beer3);
    }

    @Override
    public List<Beer> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Beer getBeerById(UUID id) {
        log.debug("getBeerById was called with id: " + id + ", in Service");
        return beerMap.get(id);
    }
}
