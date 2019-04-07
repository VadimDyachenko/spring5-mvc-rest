package ua.vadym.spring5mvcrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vadym.spring5mvcrest.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
