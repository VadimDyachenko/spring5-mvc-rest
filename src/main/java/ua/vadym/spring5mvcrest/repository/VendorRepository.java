package ua.vadym.spring5mvcrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vadym.spring5mvcrest.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
