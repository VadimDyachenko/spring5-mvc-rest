package ua.vadym.spring5mvcrest.services;

import org.springframework.stereotype.Service;
import ua.vadym.spring5mvcrest.api.v1.model.CustomerDTO;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return null;
    }
}
