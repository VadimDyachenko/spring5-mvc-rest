package ua.vadym.spring5mvcrest.bootstrap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.vadym.spring5mvcrest.domain.Category;
import ua.vadym.spring5mvcrest.repository.CategoryRepository;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootstrapTest {

    @Autowired
    private CategoryRepository repository;

    @Test
    public void shouldLoadDataOnStartup() {
        //given
        Category fruits = Category.builder().name("Fruits").build();

        //when
        List<Category> categories = repository.findAll();

        //then
        assertEquals(5, categories.size());
        assertTrue(categories.contains(fruits));
    }
}