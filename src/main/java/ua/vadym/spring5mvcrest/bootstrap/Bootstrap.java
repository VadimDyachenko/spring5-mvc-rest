package ua.vadym.spring5mvcrest.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ua.vadym.spring5mvcrest.domain.Category;
import ua.vadym.spring5mvcrest.domain.Customer;
import ua.vadym.spring5mvcrest.repository.CategoryRepository;
import ua.vadym.spring5mvcrest.repository.CustomerRepository;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final CustomerRepository customerRepository;

    public Bootstrap(CategoryRepository categoryRepository, CustomerRepository customerRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public void run(String... args) {
        loadCategories();
        loadCustomers();
    }

    private void loadCustomers() {
        Customer ironman = Customer.builder().firstname("Iron").lastname("Man").build();
        Customer superman = Customer.builder().firstname("Super").lastname("Man").build();
        Customer bob = Customer.builder().firstname("Just").lastname("Bob").build();

        List<Customer> customers = Arrays.asList(ironman, superman, bob);

        customerRepository.saveAll(customers);
        log.info("Customer data loaded, {} items", customerRepository.count());
    }

    private void loadCategories() {
        Category fruits = Category.builder().name("Fruits").build();
        Category dried = Category.builder().name("Dried").build();
        Category fresh = Category.builder().name("Fresh").build();
        Category exotic = Category.builder().name("Exotic").build();
        Category nuts = Category.builder().name("Nuts").build();

        List<Category> categories = Arrays.asList(fruits, dried, fresh, exotic, nuts);

        categoryRepository.saveAll(categories);
        log.info("Category data loaded, {} items", categoryRepository.count());
    }
}
