package ua.vadym.controllers.v1;

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
public class VendorControllerTest extends AbstractControllerTest {

    private static final String BASE_URL = "/api/v1/vendors/";
    private static final long ID_1 = 1L;
    private static final long ID_2 = 2L;
    private static final long ID_5 = 5L;

    @Test
    @DatabaseSetup("/dbunit/VendorControllerTest/initial.xml")
    public void getAllVendors() throws Exception {
        this.mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/VendorControllerTest/getAllVendors_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/VendorControllerTest/initial.xml")
    public void getVendorById() throws Exception {
        mockMvc.perform(get(BASE_URL + ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/VendorControllerTest/getVendorById_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/VendorControllerTest/initial.xml")
    public void getVendorById_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + ID_5)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonFromFile("json/VendorControllerTest/getVendorById_notFoundError.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/VendorControllerTest/initial.xml")
    @ExpectedDatabase(
            value = "/dbunit/VendorControllerTest/createNewVendor_expected.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    public void createNewVendor() throws Exception {
        String request = new JsonFileReader("json/VendorControllerTest/createNewVendor_request.json").content();

        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated())
                .andExpect(jsonFromFile("json/VendorControllerTest/createNewVendor_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/VendorControllerTest/initial.xml")
    @ExpectedDatabase(
            value = "/dbunit/VendorControllerTest/updateVendor_expected.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    public void updateVendor() throws Exception {
        String request = new JsonFileReader("json/VendorControllerTest/updateVendor_request.json").content();

        mockMvc.perform(put(BASE_URL + ID_2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/VendorControllerTest/updateVendor_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/VendorControllerTest/initial.xml")
    @ExpectedDatabase(
            value = "/dbunit/VendorControllerTest/patchVendor_expected.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    public void patchVendor() throws Exception {
        String request = new JsonFileReader("json/VendorControllerTest/patchVendor_request.json").content();

        mockMvc.perform(patch(BASE_URL + ID_1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/VendorControllerTest/patchVendor_expected.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/VendorControllerTest/initial.xml")
    public void patchVendor_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get(BASE_URL + ID_5)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonFromFile("json/VendorControllerTest/patchVendor_notFoundError.json"));
    }

    @Test
    @DatabaseSetup("/dbunit/VendorControllerTest/initial.xml")
    @ExpectedDatabase(
            value = "/dbunit/VendorControllerTest/deleteVendor_expected.xml",
            assertionMode = NON_STRICT_UNORDERED
    )
    public void deleteVendor() throws Exception {
        mockMvc.perform(delete(BASE_URL + ID_1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
