package ua.vadym.spring5mvcrest.services;

import ua.vadym.spring5mvcrest.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();

    CustomerDTO getCustomerById(long id);
}
