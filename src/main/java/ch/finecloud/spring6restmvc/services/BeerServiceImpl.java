package ch.finecloud.spring6restmvc.services;

import ch.finecloud.spring6restmvc.model.BeerDTO;
import ch.finecloud.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        this.beerMap = new HashMap<>();

        BeerDTO beerDTO1 = BeerDTO.builder()
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

        BeerDTO beerDTO2 = BeerDTO.builder()
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

        BeerDTO beerDTO3 = BeerDTO.builder()
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

        beerMap.put(beerDTO1.getId(), beerDTO1);
        beerMap.put(beerDTO2.getId(), beerDTO2);
        beerMap.put(beerDTO3.getId(), beerDTO3);
    }

    @Override
    public List<BeerDTO> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("getBeerById was called with id: " + id + ", in Service");
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
        BeerDTO savedBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .upc(beerDTO.getUpc())
                .price(beerDTO.getPrice())
                .build();

        beerMap.put(savedBeerDTO.getId(), savedBeerDTO);
        return savedBeerDTO;
    }

    @Override
    public void updateBeerById(UUID beerId, BeerDTO beerDTO) {
        BeerDTO existingBeerDTO = beerMap.get(beerId);
        existingBeerDTO.setBeerName(beerDTO.getBeerName());
        existingBeerDTO.setPrice(beerDTO.getPrice());
        existingBeerDTO.setUpc(beerDTO.getUpc());
        existingBeerDTO.setQuantityOnHand(beerDTO.getQuantityOnHand());
        beerMap.put(existingBeerDTO.getId(), existingBeerDTO);
    }

    @Override
    public void deleteById(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public void patchBeerById(UUID beerId, BeerDTO beerDTO) {
        BeerDTO existingBeerDTO = beerMap.get(beerId);

        if(StringUtils.hasText(beerDTO.getBeerName())) {
            existingBeerDTO.setBeerName(beerDTO.getBeerName());
        }

        if(beerDTO.getBeerStyle() != null) {
            existingBeerDTO.setBeerStyle(beerDTO.getBeerStyle());
        }

        if(beerDTO.getPrice() != null) {
            existingBeerDTO.setPrice(beerDTO.getPrice());
        }

        if(beerDTO.getQuantityOnHand() != null) {
            existingBeerDTO.setQuantityOnHand(beerDTO.getQuantityOnHand());
        }

        if(StringUtils.hasText(beerDTO.getUpc())) {
            existingBeerDTO.setUpc(beerDTO.getUpc());
        }
    }
}
