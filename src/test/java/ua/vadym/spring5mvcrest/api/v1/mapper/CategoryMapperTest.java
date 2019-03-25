package ua.vadym.spring5mvcrest.api.v1.mapper;

import org.junit.Test;
import ua.vadym.spring5mvcrest.api.v1.model.CategoryDTO;
import ua.vadym.spring5mvcrest.domain.Category;

import static org.junit.Assert.assertEquals;

public class CategoryMapperTest {

    private static final String NAME = "Fruits";
    private static final long ID = 1L;

    private CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    @Test
    public void mapCategoryToCategoryDto() {
        //given
        Category category = Category.builder().name(NAME).id(ID).build();

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDto(category);

        //then
        assertEquals(Long.valueOf(ID), categoryDTO.getId());
        assertEquals(NAME, categoryDTO.getName());

    }
}