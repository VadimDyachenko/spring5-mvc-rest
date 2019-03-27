package ua.vadym.spring5mvcrest.controllers.v1;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.springframework.http.MediaType;
import testtool.JsonFileReader;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testtool.JsonFileMatcher.jsonFromFile;

@DatabaseSetup("/dbunit/empty.xml")
public class CustomerControllerTest extends AbstractControllerTest {

    private static final String URL_CUSTOMERS = "/api/v1/customers/";
    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/getAllCustomers.xml")
    public void getAllCustomers() throws Exception {
        this.mockMvc.perform(get(URL_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CustomerControllerTest/getAllCustomers_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/getCustomerById.xml")
    public void getCustomerById() throws Exception {
        mockMvc.perform(get(URL_CUSTOMERS + ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CustomerControllerTest/getCustomerById_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/createNewCustomer_initial.xml")
    @ExpectedDatabase(
            value = "/dbunit/CustomerControllerTest/createNewCustomer_expected.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    public void createNewCustomer() throws Exception {
        String request = new JsonFileReader("json/CustomerControllerTest/createNewCustomer_request.json").content();

        mockMvc.perform(post(URL_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonFromFile("json/CustomerControllerTest/createNewCustomer_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/updateCustomer_initial.xml")
    @ExpectedDatabase(
            value = "/dbunit/CustomerControllerTest/updateCustomer_expected.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    public void updateCustomer() throws Exception {
        String request = new JsonFileReader("json/CustomerControllerTest/updateCustomer_request.json").content();

        mockMvc.perform(put(URL_CUSTOMERS+ ID_2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CustomerControllerTest/updateCustomer_expected.json"));
    }
}
