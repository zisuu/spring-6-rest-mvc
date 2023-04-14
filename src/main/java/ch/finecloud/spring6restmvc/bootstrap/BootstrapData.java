package ch.finecloud.spring6restmvc.bootstrap;

import ch.finecloud.spring6restmvc.entities.Beer;
import ch.finecloud.spring6restmvc.entities.Customer;
import ch.finecloud.spring6restmvc.model.BeerCSVRecord;
import ch.finecloud.spring6restmvc.model.BeerStyle;
import ch.finecloud.spring6restmvc.repositories.BeerRepository;
import ch.finecloud.spring6restmvc.repositories.CustomerRepository;
import ch.finecloud.spring6restmvc.services.BeerCsvService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCsvData();
        loadCustomerData();
    }

    private void loadCsvData() throws FileNotFoundException {
        if (beerRepository.count() < 10) {
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

            List<BeerCSVRecord> beerCSVRecords = beerCsvService.convertCSV(file);

            beerCSVRecords.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(), 50))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(beerCSVRecord.getRow().toString())
                        .quantityOnHand(beerCSVRecord.getCount())
                        .build());
            });

        }
    }

    private void loadBeerData() {
        if (beerRepository.count() == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Moudi Bier")
                    .beerStyle(BeerStyle.NEIPA)
                    .upc("123456")
                    .price(new BigDecimal(12.99))
                    .quantityOnHand(100)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Moudi Bier")
                    .beerStyle(BeerStyle.PILSNER)
                    .upc("9567")
                    .price(new BigDecimal(10.50))
                    .quantityOnHand(100)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Moudi Bier")
                    .beerStyle(BeerStyle.ALE)
                    .upc("45452")
                    .price(new BigDecimal(9.99))
                    .quantityOnHand(100)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();
            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);
        }
    }

    private void loadCustomerData() {
        if (customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()
                    .customerName("Hans Fischer")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            Customer customer2 = Customer.builder()
                    .customerName("Peter Muster")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            Customer customer3 = Customer.builder()
                    .customerName("Max Müller")
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();
            customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3));
        }
    }

}
