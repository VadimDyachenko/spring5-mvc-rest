package ua.vadym.spring5mvcrest.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import testtool.JsonFileReader;
import ua.vadym.spring5mvcrest.api.v1.model.CustomerDTO;
import ua.vadym.spring5mvcrest.services.CustomerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest {

    @Mock
    private CustomerService service;

    @InjectMocks
    private CustomerController controller;

    private MockMvc mockMvc;

    private List<CustomerDTO> customers;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        CustomerDTO first = new CustomerDTO();
        first.setId(1L);
        first.setFirstname("Iron");
        first.setLastname("Man");
        first.setUrl("/api/v1/customer/1");

        CustomerDTO second = new CustomerDTO();
        second.setId(2L);
        second.setFirstname("Super");
        second.setLastname("Man");
        second.setUrl("/api/v1/customer/2");

        customers = Arrays.asList(first, second);
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        when(service.getAllCustomers()).thenReturn(customers);

        String expected = new JsonFileReader("json/CategoryControllerTest/getAllCustomers_expected.json").content();

        //then-when
        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}
