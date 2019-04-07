package ua.vadym.api.v1.model;

import java.util.Collections;
import java.util.List;

public class CategoryListDTO {
    private final List<CategoryDTO> categories;

    public CategoryListDTO(List<CategoryDTO> categories) {
        this.categories = categories == null ? Collections.emptyList() : categories;
    }

    public List<CategoryDTO> getCategories() {
        return this.categories;
    }
}
