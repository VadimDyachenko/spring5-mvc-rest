package ua.vadym.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.vadym.domain.Vendor;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
