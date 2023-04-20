package ch.finecloud.spring6restmvc.repositories;

import ch.finecloud.spring6restmvc.entities.Beer;
import ch.finecloud.spring6restmvc.entities.Customer;
import ch.finecloud.spring6restmvc.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);
    }

    @Test
    void testBeerOrders() {
        System.out.println(beerOrderRepository.count());
        System.out.println(customerRepository.count());
        System.out.println(beerRepository.count());
        System.out.println(testCustomer.getCustomerName());
        System.out.println(testBeer.getBeerName());
    }
}