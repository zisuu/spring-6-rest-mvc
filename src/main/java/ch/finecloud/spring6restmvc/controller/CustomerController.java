package ch.finecloud.spring6restmvc.controller;

import ch.finecloud.spring6restmvc.model.Beer;
import ch.finecloud.spring6restmvc.model.Customer;
import ch.finecloud.spring6restmvc.services.BeerService;
import ch.finecloud.spring6restmvc.services.CustomerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers() {
        log.debug("listCustomers was called, in Controller");
        return customerService.listCustomers();
    }

    @RequestMapping(value = "{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable("customerId") UUID customerId) {
        log.debug("getCustomerById was called with id: " + customerId + ", in Controller");
        return customerService.getCustomerById(customerId);
    }

}
