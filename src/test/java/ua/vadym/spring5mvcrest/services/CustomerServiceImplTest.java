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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

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
    }

    @Test
    public void findById() {
        //given
        Customer customer = Customer.builder().id(5L).firstname("Iron").lastname("Man").build();
        when(repository.findById(5L)).thenReturn(Optional.of(customer));

        //when
        CustomerDTO actual = service.getCustomerById(5L);

        //then
        assertEquals(Long.valueOf(5L), actual.getId());
        assertEquals("Iron", actual.getFirstname());
        assertEquals("Man", actual.getLastname());
        assertEquals("/api/v1/customer/5", actual.getUrl());

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
    }
}