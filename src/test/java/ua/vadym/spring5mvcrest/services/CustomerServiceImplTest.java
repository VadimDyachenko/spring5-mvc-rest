package ua.vadym.spring5mvcrest.services;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    private static final String FIRST_NAME = "Iron";
    private static final String NEW_NAME = "New Name";
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

        Customer toSaveCustomer = Customer.builder().firstname(FIRST_NAME).lastname(LAST_NAME).build();
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
        verify(repository, times(1)).save(toSaveCustomer);
    }

    @Test
    public void saveCustomerByDTO() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        Customer savedCustomer = Customer.builder().id(3L).firstname(FIRST_NAME).lastname(LAST_NAME).build();
        when(repository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO expected = new CustomerDTO();
        expected.setFirstname(FIRST_NAME);
        expected.setLastname(LAST_NAME);
        expected.setUrl("/api/v1/customers/3");

        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        //when
        CustomerDTO actual = service.saveCustomerByDTO(3L, customerDTO);

        //then
        assertThat(actual, is(expected));
        verify(repository, times(1)).save(argumentCaptor.capture());
        assertEquals(Long.valueOf(3L), argumentCaptor.getValue().getId());
    }

    @Test
    public void patchCustomer_shouldUpdateFirstName() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(NEW_NAME);

        long id = 1L;
        Customer initialCustomer = Customer.builder().id(id).firstname(FIRST_NAME).lastname(LAST_NAME).build();
        Customer patchedCustomer = Customer.builder().id(id).firstname(NEW_NAME).lastname(LAST_NAME).build();
        when(repository.findById(id)).thenReturn(Optional.of(initialCustomer));

        Customer savedCustomer = Customer.builder().id(id).firstname(NEW_NAME).lastname(LAST_NAME).build();
        when(repository.save(any(Customer.class))).thenReturn(savedCustomer);
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        CustomerDTO expected = new CustomerDTO();
        expected.setFirstname(NEW_NAME);
        expected.setLastname(LAST_NAME);
        expected.setUrl("/api/v1/customers/1");

        //when
        CustomerDTO actual = service.patchCustomer(id, customerDTO);

        //then
        assertThat(actual, is(expected));
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(argumentCaptor.capture());
        assertThat(patchedCustomer, is(argumentCaptor.getValue()));
    }

    @Test
    public void patchCustomer_shouldUpdateLastName() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(NEW_NAME);

        long id = 1L;
        Customer initialCustomer = Customer.builder().id(id).firstname(FIRST_NAME).lastname(LAST_NAME).build();
        Customer patchedCustomer = Customer.builder().id(id).firstname(FIRST_NAME).lastname(NEW_NAME).build();
        when(repository.findById(id)).thenReturn(Optional.of(initialCustomer));

        Customer savedCustomer = Customer.builder().id(id).firstname(FIRST_NAME).lastname(NEW_NAME).build();
        when(repository.save(any(Customer.class))).thenReturn(savedCustomer);
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        CustomerDTO expected = new CustomerDTO();
        expected.setFirstname(FIRST_NAME);
        expected.setLastname(NEW_NAME);
        expected.setUrl("/api/v1/customers/1");

        //when
        CustomerDTO actual = service.patchCustomer(id, customerDTO);

        //then
        assertThat(actual, is(expected));
        verify(repository, times(1)).findById(id);
        verify(repository, times(1)).save(argumentCaptor.capture());
        assertThat(patchedCustomer, is(argumentCaptor.getValue()));
    }

    @Test
    public void patchCustomer_shouldThrowExceptionIfNotFound() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(NEW_NAME);

        long id = 1L;
        when(repository.findById(id)).thenReturn(Optional.empty());

        //then-when
        expectedException.expect(RuntimeException.class);
        expectedException.expectMessage("Customer with id:" + id + " not found.");

        service.patchCustomer(id, customerDTO);

        verify(repository, times(1)).findById(id);
        verify(repository, never()).save(any(Customer.class));
    }
}