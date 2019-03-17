package ua.vadym.spring5mvcrest.services;

import ua.vadym.spring5mvcrest.api.v1.model.CategoryDTO;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getAllCategory();

    CategoryDTO getCategoryByName(String name);
}
