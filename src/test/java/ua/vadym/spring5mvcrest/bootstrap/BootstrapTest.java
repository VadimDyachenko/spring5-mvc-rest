package ua.vadym.spring5mvcrest.bootstrap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.vadym.spring5mvcrest.domain.Category;
import ua.vadym.spring5mvcrest.domain.Customer;
import ua.vadym.spring5mvcrest.repository.CategoryRepository;
import ua.vadym.spring5mvcrest.repository.CustomerRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootstrapTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void shouldLoadDataOnStartup() {
        //when
        List<Category> categories = categoryRepository.findAll();
        List<Customer> customers =  customerRepository.findAll();

        //then
        assertEquals(5, categories.size());
        assertEquals(3, customers.size());
    }
}