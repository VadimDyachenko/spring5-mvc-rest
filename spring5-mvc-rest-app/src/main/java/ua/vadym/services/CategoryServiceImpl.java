package ua.vadym.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.vadym.api.v1.mapper.CategoryMapper;
import ua.vadym.api.v1.model.CategoryDTO;
import ua.vadym.domain.Category;
import ua.vadym.exceptions.ApplicationException;
import ua.vadym.exceptions.ResourceNotFoundError;
import ua.vadym.repository.CategoryRepository;

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
    @Transactional(readOnly = true)
    public List<CategoryDTO> getAllCategories() {
        return repository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryDTO getCategoryByName(String name) {
        Category category = repository.findByName(name)
                .orElseThrow(() -> new ApplicationException(
                        new ResourceNotFoundError("Category '" + name + "' not found."))
                );
        return categoryMapper.categoryToCategoryDto(category);
    }
}
