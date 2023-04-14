package ch.finecloud.spring6restmvc.repositories;


import ch.finecloud.spring6restmvc.bootstrap.BootstrapData;
import ch.finecloud.spring6restmvc.entities.Beer;
import ch.finecloud.spring6restmvc.model.BeerStyle;
import ch.finecloud.spring6restmvc.services.BeerCsvServiceImpl;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testGetBeerByStyle() {
        List<Beer> list = beerRepository.findAllByBeerStyle(BeerStyle.LAGER);
        assertThat(list.size()).isEqualTo(39);
    }

    @Test
    void testGetBeerByName() {
        List<Beer> list = beerRepository.findAllByBeerNameIsLikeIgnoreCase("%IPA%");
        assertThat(list.size()).isEqualTo(336);
    }

    @Test
    void testBeerNameTooLong() {
        assertThrows(ConstraintViolationException.class, () -> {
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("TestBeer23482347982748923748923784723894782937482374723847238479")
                    .beerStyle(BeerStyle.ALE)
                    .upc("123456789012")
                    .price(new BigDecimal(12.99))
                    .build());
            beerRepository.flush();
        });
    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                .beerName("TestBeer")
                .beerStyle(BeerStyle.ALE)
                .upc("123456789012")
                .price(new BigDecimal(12.99))
                .build());

        beerRepository.flush();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }
}