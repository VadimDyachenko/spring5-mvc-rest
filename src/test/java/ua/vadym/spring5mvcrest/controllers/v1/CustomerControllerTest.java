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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testtool.JsonFileMatcher.jsonFromFile;

public class CustomerControllerTest {

    private static final String FIRST_NAME = "Iron";
    private static final String LAST_NAME = "Man";
    private static final String URL_CUSTOMERS = "/api/v1/customers/";
    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;

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
        firstCustomerDTO.setFirstname(FIRST_NAME);
        firstCustomerDTO.setLastname(LAST_NAME);
        firstCustomerDTO.setUrl(URL_CUSTOMERS + ID_1);

        secondCustomerDTO = new CustomerDTO();
        secondCustomerDTO.setFirstname("Super");
        secondCustomerDTO.setLastname(LAST_NAME);
        secondCustomerDTO.setUrl(URL_CUSTOMERS + ID_2);

        customers = Arrays.asList(firstCustomerDTO, secondCustomerDTO);
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        when(service.getAllCustomers()).thenReturn(customers);

        //then-when
        mockMvc.perform(get(URL_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CustomerControllerTest/getAllCustomers_expected.json"));
    }

    @Test
    public void getCustomerById() throws Exception {
        //given
        when(service.getCustomerById(1L)).thenReturn(firstCustomerDTO);

        //then-when
        mockMvc.perform(get(URL_CUSTOMERS + ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CustomerControllerTest/getCustomerById_expected.json"));
    }

    @Test
    public void createNewCustomer() throws Exception {
        //given
        String request = new JsonFileReader("json/CustomerControllerTest/createNewCustomer_request.json").content();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        CustomerDTO returnCustomerDTO = new CustomerDTO();
        returnCustomerDTO.setFirstname(FIRST_NAME);
        returnCustomerDTO.setLastname(LAST_NAME);
        returnCustomerDTO.setUrl(URL_CUSTOMERS + ID_2);

        when(service.createNewCustomer(customerDTO)).thenReturn(returnCustomerDTO);

        //then-when
        mockMvc.perform(post(URL_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonFromFile("json/CustomerControllerTest/createNewCustomer_expected.json"));
    }
}
