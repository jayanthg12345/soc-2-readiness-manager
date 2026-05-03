package com.campuspe.soc2_readiness_manager.repository;

import com.campuspe.soc2_readiness_manager.entity.ComplianceRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface ComplianceRepository extends JpaRepository<ComplianceRecord, UUID> {

    // Filter by status
    List<ComplianceRecord> findByStatus(String status);

    // Pagination + status
    Page<ComplianceRecord> findByStatus(String status, Pageable pageable);

    // Date range
    List<ComplianceRecord> findByCreatedAtBetween(
            LocalDateTime start,
            LocalDateTime end
    );

    // Search
    @Query("SELECT c FROM ComplianceRecord c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<ComplianceRecord> searchByTitle(@Param("keyword") String keyword);

    // Search with pagination
    @Query("SELECT c FROM ComplianceRecord c WHERE LOWER(c.title) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    Page<ComplianceRecord> searchWithPagination(
            @Param("keyword") String keyword,
            Pageable pageable
    );
}