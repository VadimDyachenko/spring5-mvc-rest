package ua.vadym.api.v1.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CustomerListDTOTest {

    @Test
    public void getCustomers_shouldReturnEmptyList() {
        CustomerListDTO list = new CustomerListDTO(null);
        assertTrue(list.getCustomers().isEmpty());
    }

    @Test
    public void getCustomers() {
        List<CustomerDTO> customerDTOList = Arrays.asList(new CustomerDTO(), new CustomerDTO());
        CustomerListDTO list = new CustomerListDTO(customerDTOList);
        assertThat(customerDTOList, is(list.getCustomers()));
    }
}