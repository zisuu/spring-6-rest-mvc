package ch.finecloud.spring6restmvc.controller;

import ch.finecloud.spring6restmvc.entities.Beer;
import ch.finecloud.spring6restmvc.entities.Customer;
import ch.finecloud.spring6restmvc.mappers.BeerMapper;
import ch.finecloud.spring6restmvc.mappers.CustomerMapper;
import ch.finecloud.spring6restmvc.model.BeerDTO;
import ch.finecloud.spring6restmvc.model.CustomerDTO;
import ch.finecloud.spring6restmvc.repositories.BeerRepository;
import ch.finecloud.spring6restmvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void testDeleteByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.deleteById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound() {
        Customer testCustomer = customerRepository.findAll().get(0);
        ResponseEntity responseEntity = customerController.deleteById(testCustomer.getId());
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        assertThat(customerRepository.findById(testCustomer.getId()).isEmpty());
    }

    @Test
    void testUpdateNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.updateById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingCustomer() {
        Customer testCustomer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(testCustomer);
        customerDTO.setId(null);
        customerDTO.setVersion(null);
        customerDTO.setCustomerName("Updated Customer");
        ResponseEntity responseEntity = customerController.updateById(testCustomer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(204));
        Customer updatedCustomer = customerRepository.findById(testCustomer.getId()).get();
        assertThat(updatedCustomer.getCustomerName()).isEqualTo("Updated Customer");
    }

    @Rollback
    @Transactional
    @Test
    void saveNewCustomerTest() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New TestCustomer")
                .build();
        ResponseEntity responseEntity = customerController.handlePost(customerDTO);
        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(201);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] locationUUID = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID savedUUID = UUID.fromString(locationUUID[4]);
        Customer customer = customerRepository.findById(savedUUID).get();
        assertThat(customer).isNotNull();
    }

    @Test
    void testCustomerByIdNotFound() {
        assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
    }

    @Test
    void testGetById() {
        Customer testCustomer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(testCustomer.getId());
        assertThat(customerDTO).isNotNull();
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> customerDTOList = customerController.listCustomers();
        assertThat(customerDTOList.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyList() {
        customerRepository.deleteAll();
        List<CustomerDTO> customerDTOList = customerController.listCustomers();
        assertThat(customerDTOList.size()).isEqualTo(0);
    }
}