package ch.finecloud.spring6restmvc;

import ch.finecloud.spring6restmvc.model.BeerCSVRecord;
import ch.finecloud.spring6restmvc.services.BeerCsvService;
import ch.finecloud.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class BeerCsvServiceImplTest {

    BeerCsvService beerCsvService = new BeerCsvServiceImpl();

    @Test
    void convertCSV() throws FileNotFoundException {
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> beerCSVRecords = beerCsvService.convertCSV(file);
        System.out.println(beerCSVRecords.size());
        assertThat(beerCSVRecords.size()).isGreaterThan(0);
    }
}
