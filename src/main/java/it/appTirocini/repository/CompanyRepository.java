package it.appTirocini.repository;

import it.appTirocini.entity.Company;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CompanyRepository extends JpaRepository<Company, UUID> {

    boolean existsByName(String name);

    Page<Company> findByNameContainingIgnoreCase(String q, Pageable pageable);
}
