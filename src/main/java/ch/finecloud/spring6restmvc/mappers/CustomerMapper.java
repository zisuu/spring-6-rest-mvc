package ch.finecloud.spring6restmvc.mappers;

import ch.finecloud.spring6restmvc.entities.Customer;
import ch.finecloud.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO customerDTO);

    CustomerDTO customerToCustomerDto(Customer customer);
}
