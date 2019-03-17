package ua.vadym.spring5mvcrest.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.vadym.spring5mvcrest.domain.Category;
import ua.vadym.spring5mvcrest.repository.CategoryRepository;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;

    public Bootstrap(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) {
        Category fruits = Category.builder().name("Fruits").build();
        Category dried = Category.builder().name("Dried").build();
        Category fresh = Category.builder().name("Fresh").build();
        Category exotic = Category.builder().name("Exotic").build();
        Category nuts = Category.builder().name("Nuts").build();

        List<Category> categories = Arrays.asList(fruits, dried, fresh, exotic, nuts);

        categoryRepository.saveAll(categories);

        log.info("Data loaded, {} items", categoryRepository.count());
    }
}
