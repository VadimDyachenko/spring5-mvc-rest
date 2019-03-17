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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CategoryControllerTest {

    private static final String NAME = "Fruits";

    @Mock
    CategoryService service;

    @InjectMocks
    CategoryController controller;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void getListCategories() throws Exception {
        //given
        CategoryDTO first = new CategoryDTO();
        first.setId(1L);
        first.setName(NAME);

        CategoryDTO second = new CategoryDTO();
        second.setId(2L);
        second.setName("Nuts");

        List<CategoryDTO> categoryDTOList = Arrays.asList(first, second);
        when(service.getAllCategories()).thenReturn(categoryDTOList);

        //then-when
        mockMvc.perform(get("/api/v1/categories/")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)));

    }

    @Test
    public void findByName_shouldReturnCategory() throws Exception {
        //given
        CategoryDTO first = new CategoryDTO();
        first.setId(1L);
        first.setName(NAME);

        when(service.getCategoryByName(anyString())).thenReturn(first);

        //then-when
        mockMvc.perform(get("/api/v1/categories/fruits")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)));

    }
}