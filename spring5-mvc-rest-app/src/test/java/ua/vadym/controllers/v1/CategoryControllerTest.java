package ua.vadym.controllers.v1;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testtool.JsonFileMatcher.jsonFromFile;

@DatabaseSetup("/dbunit/CategoryControllerTest/categories_initial.xml")
public class CategoryControllerTest extends AbstractControllerTest{

    private static final String FRUITS = "Fruits";
    private static final String BASE_URL = "/api/v1/categories/";

    @Test
    public void getListCategories() throws Exception {
        mockMvc.perform(get(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CategoryControllerTest/getListCategories_expected.json"));
    }

    @Test
    public void findByName() throws Exception {
        mockMvc.perform(get(BASE_URL + FRUITS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CategoryControllerTest/findByName_expected.json"));
    }

    @Test
    public void findByName_shouldReturnNotFoundError() throws Exception {
        mockMvc.perform(get(BASE_URL + "Exotic fruits")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonFromFile("json/CategoryControllerTest/findByName_notFoundError.json"));

    }
}