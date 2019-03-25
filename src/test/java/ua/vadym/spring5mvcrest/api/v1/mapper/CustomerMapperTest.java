package ua.vadym.spring5mvcrest.api.v1.mapper;

import org.junit.Test;
import ua.vadym.spring5mvcrest.api.v1.model.CustomerDTO;
import ua.vadym.spring5mvcrest.domain.Customer;

import static org.junit.Assert.assertEquals;

public class CustomerMapperTest {

    private static final long ID = 1L;
    private static final String FIRST_NAME = "Iron";
    private static final String LAST_NAME = "Man";
    private static final String URL = "http://url.com";

    private CustomerMapper customerMapper = CustomerMapper.INSTANCE;

    @Test
    public void mapCustomerToCustomerDTO() {
        //given
        Customer customer = Customer.builder().id(ID).firstname(FIRST_NAME).lastname(LAST_NAME).url(URL).build();

        //when
        CustomerDTO customerDTO = customerMapper.CustomerToCustomerDto(customer);

        //then
        assertEquals(Long.valueOf(ID), customerDTO.getId());
        assertEquals(FIRST_NAME, customerDTO.getFirstname());
        assertEquals(LAST_NAME, customerDTO.getLastname());
        assertEquals(URL, customerDTO.getUrl());
    }
}