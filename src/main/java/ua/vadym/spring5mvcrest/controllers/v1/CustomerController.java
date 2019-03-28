package ua.vadym.spring5mvcrest.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ua.vadym.spring5mvcrest.api.v1.model.CustomerDTO;
import ua.vadym.spring5mvcrest.api.v1.model.CustomerListDTO;
import ua.vadym.spring5mvcrest.services.CustomerService;

@Api(description = "There is simple Customer controller")
@RestController
@RequestMapping("/api/v1/customers/")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @ApiOperation(value = "Get a list of customers.")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CustomerListDTO getAllCustomers() {
        return new CustomerListDTO(service.getAllCustomers());
    }

    @ApiOperation(value = "Get Customer by id.")
    @GetMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        return service.getCustomerById(id);
    }

    @ApiOperation(value = "Create new Customer.")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerDTO createNewCustomer(@RequestBody CustomerDTO customer) {
        return service.createNewCustomer(customer);
    }

    @ApiOperation(value = "Update Customer.")
    @PutMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customer) {
        return service.saveCustomerByDTO(id, customer);
    }

    @ApiOperation(value = "Patch Customer's fields.")
    @PatchMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public CustomerDTO patchCustomer(@PathVariable Long id, @RequestBody CustomerDTO customer) {
        return service.patchCustomer(id, customer);
    }

    @ApiOperation(value = "Delete Customer by id.")
    @DeleteMapping({"{id}"})
    @ResponseStatus(HttpStatus.OK)
    public void deleteCustomer(@PathVariable Long id) {
        service.deleteCustomerById(id);
    }
}
