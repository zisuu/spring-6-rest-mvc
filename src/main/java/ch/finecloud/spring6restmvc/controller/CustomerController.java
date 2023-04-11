package ch.finecloud.spring6restmvc.controller;

import ch.finecloud.spring6restmvc.model.CustomerDTO;
import ch.finecloud.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
public class CustomerController {
    public static final String BASE_URL = "/api/v1/customers";
    public static final String BASE_URL_ID = BASE_URL + "/{customerId}";

    private final CustomerService customerService;

    @PatchMapping(BASE_URL_ID)
    public ResponseEntity updateCustomerPatchById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customerDTO) {
        customerService.patchCustomerById(customerId, customerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BASE_URL_ID)
    public ResponseEntity deleteById(@PathVariable("customerId") UUID customerId) {
        if (!customerService.deleteById(customerId)) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BASE_URL_ID)
    public ResponseEntity updateById(@PathVariable("customerId") UUID customerId, @RequestBody CustomerDTO customerDTO) {
        if (customerService.updateCustomerById(customerId, customerDTO).isEmpty()) {
            throw new NotFoundException();
        }
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping(BASE_URL)
    public ResponseEntity handlePost(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO savedCustomerDTO = customerService.saveNewCustomer(customerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/customers/" + savedCustomerDTO.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping(BASE_URL)
    public List<CustomerDTO> listCustomers() {
        log.debug("listCustomers was called, in Controller");
        return customerService.listCustomers();
    }

    @GetMapping(BASE_URL_ID)
    public CustomerDTO getCustomerById(@PathVariable("customerId") UUID customerId) {
        log.debug("getCustomerById was called with id: " + customerId + ", in Controller");
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

}
