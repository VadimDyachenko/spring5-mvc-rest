package ua.vadym.spring5mvcrest.services;

import org.springframework.stereotype.Service;
import ua.vadym.spring5mvcrest.api.v1.mapper.CategoryMapper;
import ua.vadym.spring5mvcrest.api.v1.model.CategoryDTO;
import ua.vadym.spring5mvcrest.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository repository, CategoryMapper categoryMapper) {
        this.repository = repository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<CategoryDTO> getAllCategory() {
        return repository.findAll()
                .stream()
                .map(categoryMapper::CategoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return categoryMapper.CategoryToCategoryDto(repository.findByName(name));
    }
}
