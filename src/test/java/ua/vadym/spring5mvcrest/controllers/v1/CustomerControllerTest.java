package ua.vadym.spring5mvcrest.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.vadym.spring5mvcrest.api.v1.model.CustomerDTO;
import ua.vadym.spring5mvcrest.services.CustomerService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testtool.JsonFileMatcher.jsonFromFile;

public class CustomerControllerTest {

    @Mock
    private CustomerService service;

    @InjectMocks
    private CustomerController controller;

    private MockMvc mockMvc;

    private List<CustomerDTO> customers;
    private CustomerDTO firstCustomerDTO;
    private CustomerDTO secondCustomerDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        firstCustomerDTO = new CustomerDTO();
        firstCustomerDTO.setFirstname("Iron");
        firstCustomerDTO.setLastname("Man");
        firstCustomerDTO.setUrl("/api/v1/customers/1");

        secondCustomerDTO = new CustomerDTO();
        secondCustomerDTO.setFirstname("Super");
        secondCustomerDTO.setLastname("Man");
        secondCustomerDTO.setUrl("/api/v1/customers/2");

        customers = Arrays.asList(firstCustomerDTO, secondCustomerDTO);
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        when(service.getAllCustomers()).thenReturn(customers);

        //then-when
        mockMvc.perform(get("/api/v1/customers/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CategoryControllerTest/getAllCustomers_expected.json"));
    }

    @Test
    public void getCustomerById() throws Exception {
        //given
        when(service.getCustomerById(1L)).thenReturn(firstCustomerDTO);

        //then-when
        mockMvc.perform(get("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CategoryControllerTest/getCustomerById_expected.json"));
    }
}
