package ua.vadym.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.vadym.api.v1.mapper.CustomerMapper;
import ua.vadym.domain.Customer;
import ua.vadym.exceptions.ApplicationException;
import ua.vadym.model.CustomerDTO;
import ua.vadym.repository.CustomerRepository;

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
    private static final String NEW_NAME = "New Name";
    private static final String LAST_NAME = "Man";
    private static final long ID = 1L;

    @Mock
    CustomerRepository repository;

    private CustomerService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new CustomerServiceImpl(repository, CustomerMapper.INSTANCE);
    }

    @Test
    public void getAllCustomers() {
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
    public void getCustomerById() {
        //given
        Customer customer = Customer.builder().id(ID).firstname(FIRST_NAME).lastname(LAST_NAME).build();
        when(repository.findById(ID)).thenReturn(Optional.of(customer));

        //when
        CustomerDTO actual = service.getCustomerById(ID);

        //then
        assertEquals(FIRST_NAME, actual.getFirstname());
        assertEquals(LAST_NAME, actual.getLastname());
        assertEquals("/api/v1/customers/1", actual.getCustomerUrl());
        verify(repository, times(1)).findById(ID);
    }

    @Test(expected = ApplicationException.class)
    public void findById_shouldThrowExceptionIfNotFound() {
        //given
        when(repository.findById(anyLong())).thenReturn(Optional.empty());

        //then-when
        service.getCustomerById(5L);
    }

    @Test
    public void createNewCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        Customer toSaveCustomer = Customer.builder().firstname(FIRST_NAME).lastname(LAST_NAME).build();
        Customer savedCustomer = Customer.builder().id(ID).firstname(FIRST_NAME).lastname(LAST_NAME).build();
        when(repository.save(any(Customer.class))).thenReturn(savedCustomer);

        CustomerDTO expected = new CustomerDTO();
        expected.setFirstname(FIRST_NAME);
        expected.setLastname(LAST_NAME);
        expected.setCustomerUrl("/api/v1/customers/1");

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
        expected.setCustomerUrl("/api/v1/customers/3");

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

        Customer initialCustomer = Customer.builder().id(ID).firstname(FIRST_NAME).lastname(LAST_NAME).build();
        Customer patchedCustomer = Customer.builder().id(ID).firstname(NEW_NAME).lastname(LAST_NAME).build();
        when(repository.findById(ID)).thenReturn(Optional.of(initialCustomer));

        Customer savedCustomer = Customer.builder().id(ID).firstname(NEW_NAME).lastname(LAST_NAME).build();
        when(repository.save(any(Customer.class))).thenReturn(savedCustomer);
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        CustomerDTO expected = new CustomerDTO();
        expected.setFirstname(NEW_NAME);
        expected.setLastname(LAST_NAME);
        expected.setCustomerUrl("/api/v1/customers/1");

        //when
        CustomerDTO actual = service.patchCustomer(ID, customerDTO);

        //then
        assertThat(actual, is(expected));
        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(argumentCaptor.capture());
        assertThat(patchedCustomer, is(argumentCaptor.getValue()));
    }

    @Test
    public void patchCustomer_shouldUpdateLastName() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(NEW_NAME);

        Customer initialCustomer = Customer.builder().id(ID).firstname(FIRST_NAME).lastname(LAST_NAME).build();
        Customer patchedCustomer = Customer.builder().id(ID).firstname(FIRST_NAME).lastname(NEW_NAME).build();
        when(repository.findById(ID)).thenReturn(Optional.of(initialCustomer));

        Customer savedCustomer = Customer.builder().id(ID).firstname(FIRST_NAME).lastname(NEW_NAME).build();
        when(repository.save(any(Customer.class))).thenReturn(savedCustomer);
        ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);

        CustomerDTO expected = new CustomerDTO();
        expected.setFirstname(FIRST_NAME);
        expected.setLastname(NEW_NAME);
        expected.setCustomerUrl("/api/v1/customers/1");

        //when
        CustomerDTO actual = service.patchCustomer(ID, customerDTO);

        //then
        assertThat(actual, is(expected));
        verify(repository, times(1)).findById(ID);
        verify(repository, times(1)).save(argumentCaptor.capture());
        assertThat(patchedCustomer, is(argumentCaptor.getValue()));
    }

    @Test(expected = ApplicationException.class)
    public void patchCustomer_shouldThrowExceptionIfNotFound() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastname(NEW_NAME);
        when(repository.findById(ID)).thenReturn(Optional.empty());

        //then-when
        service.patchCustomer(ID, customerDTO);
    }

    @Test
    public void deleteCustomerById() {
        //when
        service.deleteCustomerById(ID);

        //then
        verify(repository, times(1)).deleteById(ID);
    }
}