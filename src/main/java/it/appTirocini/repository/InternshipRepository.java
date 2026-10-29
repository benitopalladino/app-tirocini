package it.appTirocini.repository;


import it.appTirocini.entity.Internship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface InternshipRepository extends JpaRepository<Internship, UUID> {

    @Query("""
            SELECT i FROM Internship i
            WHERE (:companyId IS NULL OR i.company.id = :companyId)
              AND (:status IS NULL OR i.status = :status)
              AND (:from IS NULL OR i.startDate >= :from)
              AND (:to IS NULL OR i.endDate <= :to)
            """)
    Page<Internship> search(
            @Param("companyId") UUID companyId,
            @Param("status") Internship.InternshipStatus status,
            @Param("from") LocalDate from,
            @Param("to") LocalDate to,
            Pageable pageable
    );

    @Query("""
            SELECT i FROM Internship i
            WHERE i.student.id = :studentId
              AND i.status = it.appTirocini.entity.Internship.InternshipStatus.ACTIVE
              AND i.startDate <= :endDate
              AND i.endDate >= :startDate
            """)
    List<Internship> findActiveOverlaps(
            @Param("studentId") UUID studentId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    boolean existsByCompanyId(UUID companyId);

    List<Internship> findByStudentId(UUID studentId);
}
