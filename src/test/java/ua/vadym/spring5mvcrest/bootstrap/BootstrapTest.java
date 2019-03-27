package ua.vadym.spring5mvcrest.bootstrap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.vadym.spring5mvcrest.repository.CategoryRepository;
import ua.vadym.spring5mvcrest.repository.CustomerRepository;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootstrapTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private Bootstrap bootstrap;

    @Test
    public void shouldLoadDataOnStartup() {
        //given
        categoryRepository.deleteAll();
        customerRepository.deleteAll();

        //when
        bootstrap.run();

        //then
        assertEquals(5, categoryRepository.findAll().size());
        assertEquals(3, customerRepository.findAll().size());
    }
}