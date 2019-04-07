package ua.vadym.spring5mvcrest.api.v1.model;

import java.util.Collections;
import java.util.List;

public class CustomerListDTO {
    private final List<CustomerDTO> customers;

    public CustomerListDTO(List<CustomerDTO> customers) {
        this.customers = customers == null ? Collections.emptyList() : customers;
    }

    public List<CustomerDTO> getCustomers() {
        return this.customers;
    }
}
