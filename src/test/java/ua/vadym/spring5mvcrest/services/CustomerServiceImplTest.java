package ua.vadym.spring5mvcrest.services;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.vadym.spring5mvcrest.api.v1.mapper.CustomerMapper;
import ua.vadym.spring5mvcrest.api.v1.model.CustomerDTO;
import ua.vadym.spring5mvcrest.domain.Customer;
import ua.vadym.spring5mvcrest.repository.CustomerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    private static final String FIRST_NAME = "Iron";
    private static final String LAST_NAME = "Man";
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    CustomerRepository repository;

    private CustomerService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new CustomerServiceImpl(repository, CustomerMapper.INSTANCE);
    }

    @Test
    public void findAll() {
        //given
        List<Customer> customers = Arrays.asList(Customer.builder().build(), Customer.builder().build());
        when(repository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> actual = service.getAllCustomers();

        //then
        assertNotNull(actual);
        assertEquals(2, actual.size());
        verify(repository, times(1)).findAll();
    }

    @Test
    public void findById() {
        //given
        long id = 5L;
        Customer customer = Customer.builder().id(id).firstname(FIRST_NAME).lastname(LAST_NAME).build();
        when(repository.findById(id)).thenReturn(Optional.of(customer));

        //when
        CustomerDTO actual = service.getCustomerById(id);

        //then
        assertEquals(FIRST_NAME, actual.getFirstname());
        assertEquals(LAST_NAME, actual.getLastname());
        assertEquals("/api/v1/customers/5", actual.getUrl());
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void findById_shouldThrowExceptionIfNotFound() {
        //given
        long id = 5L;
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //then-when
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Customer with id:" + id + " not found.");

        service.getCustomerById(id);
        verify(repository, times(1)).findById(id);
    }

    @Test
    public void createNewCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        Customer customer = Customer.builder().firstname(FIRST_NAME).lastname(LAST_NAME).build();
        Customer savedCustomer = Customer.builder().id(3L).firstname(FIRST_NAME).lastname(LAST_NAME).build();
        when(repository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO expected = new CustomerDTO();
        expected.setFirstname(FIRST_NAME);
        expected.setLastname(LAST_NAME);
        expected.setUrl("/api/v1/customers/3");

        //when
        CustomerDTO actual = service.createNewCustomer(customerDTO);

        //then
        assertThat(actual, is(expected));
        verify(repository, times(1)).save(customer);
    }
}