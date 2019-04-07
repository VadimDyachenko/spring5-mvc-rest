package ua.vadym.bootstrap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import ua.vadym.repository.CategoryRepository;
import ua.vadym.repository.CustomerRepository;
import ua.vadym.repository.VendorRepository;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootstrapTest {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private Bootstrap bootstrap;

    @Test
    public void shouldLoadDataOnStartup() {
        //given
        categoryRepository.deleteAll();
        customerRepository.deleteAll();
        vendorRepository.deleteAll();

        //when
        bootstrap.run();

        //then
        assertEquals(5, categoryRepository.findAll().size());
        assertEquals(3, customerRepository.findAll().size());
        assertEquals(2, vendorRepository.findAll().size());
    }
}