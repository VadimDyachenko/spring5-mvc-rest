package ua.vadym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vadym.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
