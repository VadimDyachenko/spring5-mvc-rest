package ua.vadym.spring5mvcrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vadym.spring5mvcrest.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
