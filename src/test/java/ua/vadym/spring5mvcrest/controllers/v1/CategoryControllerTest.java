package ua.vadym.spring5mvcrest.controllers.v1;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ua.vadym.spring5mvcrest.api.v1.model.CategoryDTO;
import ua.vadym.spring5mvcrest.services.CategoryService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static testtool.JsonFileMatcher.jsonFromFile;

public class CategoryControllerTest {

    private static final String FRUITS = "Fruits";
    private static final String NUTS = "Nuts";
    private static final String URL_CATEGORIES = "/api/v1/categories/";

    @Mock
    CategoryService service;

    @InjectMocks
    CategoryController controller;

    private MockMvc mockMvc;

    private CategoryDTO firstCategoryDTO;
    private CategoryDTO secondCategoryDTO;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        firstCategoryDTO = new CategoryDTO();
        firstCategoryDTO.setId(1L);
        firstCategoryDTO.setName(FRUITS);

        secondCategoryDTO = new CategoryDTO();
        secondCategoryDTO.setId(2L);
        secondCategoryDTO.setName(NUTS);
    }

    @Test
    public void getListCategories() throws Exception {
        //given
        List<CategoryDTO> categoryDTOList = Arrays.asList(firstCategoryDTO, secondCategoryDTO);
        when(service.getAllCategories()).thenReturn(categoryDTOList);

        //then-when
        mockMvc.perform(get(URL_CATEGORIES)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CategoryControllerTest/getListCategories_expected.json"));

    }

    @Test
    public void findByName() throws Exception {
        //given
        when(service.getCategoryByName(anyString())).thenReturn(firstCategoryDTO);

        //then-when
        mockMvc.perform(get(URL_CATEGORIES + FRUITS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonFromFile("json/CategoryControllerTest/findByName_expected.json"));

    }
}