package com.campuspe.soc2_readiness_manager.service;

import com.campuspe.soc2_readiness_manager.entity.ComplianceRecord;
import com.campuspe.soc2_readiness_manager.repository.ComplianceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplianceService {

    private final ComplianceRepository repo;

    public ComplianceRecord updateRecord(UUID id, ComplianceRecord updated) {
        ComplianceRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        record.setTitle(updated.getTitle());
        record.setDescription(updated.getDescription());
        record.setStatus(updated.getStatus());
        record.setScore(updated.getScore());

        return repo.save(record);
    }

    public void softDelete(UUID id) {
        ComplianceRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Not found"));

        record.setDeleted(true);
        repo.save(record);
    }
}