package ua.vadym.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vadym.api.v1.mapper.CustomerMapper;
import ua.vadym.domain.Customer;
import ua.vadym.exceptions.ApplicationException;
import ua.vadym.exceptions.ResourceNotFoundError;
import ua.vadym.model.CustomerDTO;
import ua.vadym.repository.CustomerRepository;

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
                .orElseThrow(() -> new ApplicationException(new ResourceNotFoundError("Customer with id:" + id + " not found.")));
    }

    @Override
    @Transactional
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);

        return getCustomerDTO(savedCustomer);
    }

    @Override
    @Transactional
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);
        Customer savedCustomer = customerRepository.save(customer);
        return getCustomerDTO(savedCustomer);
    }

    @Override
    @Transactional
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        Customer patchedCustomer = customerRepository.findById(id)
                .map(customer -> mapCustomerFields(customer, customerDTO))
                .orElseThrow(() -> new ApplicationException(new ResourceNotFoundError("Customer with id:" + id + " not found.")));

        return getCustomerDTO(customerRepository.save(patchedCustomer));
    }

    @Override
    @Transactional
    public void deleteCustomerById(long id) {
        customerRepository.deleteById(id);
    }

    private Customer mapCustomerFields(Customer customer, CustomerDTO customerDTO) {
        String firstname = customerDTO.getFirstname();
        String lastname = customerDTO.getLastname();
        if (firstname != null) {
            customer.setFirstname(firstname);
        }
        if (lastname != null) {
            customer.setLastname(lastname);
        }
        return customer;
    }

    private CustomerDTO getCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = customerMapper.customerToCustomerDto(customer);
        customerDTO.setCustomerUrl("/api/v1/customers/" + customer.getId());
        return customerDTO;
    }
}
