package ua.vadym.spring5mvcrest.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vadym.spring5mvcrest.api.v1.mapper.CustomerMapper;
import ua.vadym.spring5mvcrest.api.v1.model.CustomerDTO;
import ua.vadym.spring5mvcrest.domain.Customer;
import ua.vadym.spring5mvcrest.repository.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(this::getCustomerDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CustomerDTO getCustomerById(long id) {
        return customerRepository.findById(id)
                .map(this::getCustomerDTO)
                .orElseThrow(() -> new RuntimeException("Customer with id:" + id + " not found."));
    }

    @Override
    @Transactional
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        return getCustomerDTO(savedCustomer);
    }

    private CustomerDTO getCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setUrl("/api/v1/customers/" + customer.getId());
        return customerDTO;
    }
}
