package ua.vadym.spring5mvcrest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.vadym.spring5mvcrest.api.v1.model.CategoryListDTO;
import ua.vadym.spring5mvcrest.api.v1.model.CustomerListDTO;
import ua.vadym.spring5mvcrest.services.CustomerService;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService service;

    public CustomerController(CustomerService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        return new ResponseEntity<>(
                new CustomerListDTO(service.getAllCustomers()), HttpStatus.OK);
    }
}
