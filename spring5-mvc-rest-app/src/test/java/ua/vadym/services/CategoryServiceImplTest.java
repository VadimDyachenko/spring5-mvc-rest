package ua.vadym.services;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.vadym.api.v1.mapper.CategoryMapper;
import ua.vadym.api.v1.model.CategoryDTO;
import ua.vadym.domain.Category;
import ua.vadym.exceptions.ApplicationException;
import ua.vadym.repository.CategoryRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {

    private static final String NAME = "Fruits";
    private static final long ID = 1L;

    private CategoryService service;

    @Mock
    CategoryRepository repository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        service = new CategoryServiceImpl(repository, CategoryMapper.INSTANCE);
    }

    @Test
    public void getAllCategories_shouldReturnListOfAllCategories() {
        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());
        when(repository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> actual = service.getAllCategories();

        //then
        assertEquals(3, actual.size());
    }

    @Test
    public void findByName_shouldReturnCategory() {
        //given
        Category category = Category.builder().name(NAME).id(ID).build();
        when(repository.findByName(NAME)).thenReturn(Optional.of(category));

        //when
        CategoryDTO actual = service.getCategoryByName(NAME);

        //then
        assertNotNull(actual);
        assertEquals(Long.valueOf(ID), actual.getId());
        assertEquals(NAME, actual.getName());
    }

    @Test(expected = ApplicationException.class)
    public void findByName_shouldThrowException() {
        //given
        when(repository.findByName(anyString())).thenReturn(Optional.empty());

        //when-then
        service.getCategoryByName(NAME);
    }
}