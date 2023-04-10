package ch.finecloud.spring6restmvc.mappers;

import ch.finecloud.spring6restmvc.entities.Beer;
import ch.finecloud.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO beerDTO);

    BeerDTO beerToBeerDto(Beer beer);
}
