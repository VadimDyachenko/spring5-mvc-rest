package ua.vadym.spring5mvcrest.controllers.v1;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;
import org.junit.Test;
import org.springframework.http.MediaType;
import testtool.JsonFileReader;

import static com.github.springtestdbunit.assertion.DatabaseAssertionMode.NON_STRICT_UNORDERED;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testtool.JsonFileMatcher.jsonFromFile;

@DatabaseSetup("/dbunit/empty.xml")
public class CustomerControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/customers/";
    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;
    private static final long ID_5 = 5L;

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/getAllCustomers.xml")
    public void getAllCustomers() throws Exception {
        this.mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CustomerControllerTest/getAllCustomers_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/getCustomerById.xml")
    public void getCustomerById() throws Exception {
        mockMvc.perform(get(BASE_URL + ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CustomerControllerTest/getCustomerById_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/getCustomerById.xml")
    public void getCustomerById_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + ID_5)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonFromFile("json/CustomerControllerTest/getCustomerById_notFoundError.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/createNewCustomer_initial.xml")
    @ExpectedDatabase(
            value = "/dbunit/CustomerControllerTest/createNewCustomer_expected.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    public void createNewCustomer() throws Exception {
        String request = new JsonFileReader("json/CustomerControllerTest/createNewCustomer_request.json").content();

        mockMvc.perform(post(BASE_URL)
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

        mockMvc.perform(put(BASE_URL + ID_2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CustomerControllerTest/updateCustomer_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/patchCustomer_initial.xml")
    @ExpectedDatabase(
            value = "/dbunit/CustomerControllerTest/patchCustomer_expected.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    public void patchCustomer() throws Exception {
        String request = new JsonFileReader("json/CustomerControllerTest/patchCustomer_request.json").content();

        mockMvc.perform(patch(BASE_URL + ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CustomerControllerTest/patchCustomer_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/patchCustomer_initial.xml")
    public void patchCustomer_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + ID_5)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonFromFile("json/CustomerControllerTest/patchCustomer_notFoundError.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/CustomerControllerTest/deleteCustomer_initial.xml")
    @ExpectedDatabase(
            value = "/dbunit/CustomerControllerTest/deleteCustomer_expected.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    public void deleteCustomer() throws Exception {
        mockMvc.perform(delete(BASE_URL + ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
