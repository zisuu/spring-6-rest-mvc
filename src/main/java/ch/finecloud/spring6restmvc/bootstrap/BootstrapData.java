package ch.finecloud.spring6restmvc.bootstrap;

import ch.finecloud.spring6restmvc.entities.Beer;
import ch.finecloud.spring6restmvc.entities.Customer;
import ch.finecloud.spring6restmvc.model.BeerStyle;
import ch.finecloud.spring6restmvc.repositories.BeerRepository;
import ch.finecloud.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();


        System.out.println("Loading Beer Data");
        System.out.println("Number of Beers: " + beerRepository.count());
        System.out.println("Loading Customer Data");
        System.out.println("Number of Customers: " + customerRepository.count());
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
            beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3));
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
