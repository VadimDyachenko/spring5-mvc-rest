package ua.vadym.spring5mvcrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vadym.spring5mvcrest.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
