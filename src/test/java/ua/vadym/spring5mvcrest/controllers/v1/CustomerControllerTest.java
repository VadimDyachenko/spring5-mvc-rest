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

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        CustomerDTO first = CustomerDTO.builder().firstname("Iron").lastname("Man").url("http://ironman.com").build();
        CustomerDTO second = CustomerDTO.builder().firstname("Super").lastname("Man").url("http://superman.com").build();
        List<CustomerDTO> customers = Arrays.asList(first, second);
        when(service.getAllCustomers()).thenReturn(customers);

        String expected = new JsonFileReader("json/CategoryControllerTest/getAllCustomers_expected.json").content();

        //then-when
        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expected));
    }
}
