package ch.finecloud.spring6restmvc.repositories;

import ch.finecloud.spring6restmvc.entities.Beer;
import ch.finecloud.spring6restmvc.entities.Category;
import ch.finecloud.spring6restmvc.entities.Customer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BeerRepository beerRepository;

    Beer testBeer;

    @BeforeEach
    void setUp() {
        testBeer = beerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testAddCategory() {
        Category savedCat = categoryRepository.save(Category.builder()
                .description("Test Category")
                .build());

        testBeer.addCategory(savedCat);
        Beer savedBeer = beerRepository.save(testBeer);
        System.out.println(savedBeer.getBeerName());
    }
}