package ua.vadym.api.v1.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class CategoryListDTOTest {
    @Test
    public void getCategories_shouldReturnEmptyList() {
        CategoryListDTO list = new CategoryListDTO(null);
        assertTrue(list.getCategories().isEmpty());
    }

    @Test
    public void getCategories() {
        List<CategoryDTO> categoryDTOList = Arrays.asList(new CategoryDTO(), new CategoryDTO());
        CategoryListDTO list = new CategoryListDTO(categoryDTOList);
        assertThat(categoryDTOList, is(list.getCategories()));
    }
}