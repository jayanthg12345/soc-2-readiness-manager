package com.campuspe.soc2_readiness_manager.controller;

import com.campuspe.soc2_readiness_manager.entity.ComplianceRecord;
import com.campuspe.soc2_readiness_manager.service.ComplianceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/records")
@RequiredArgsConstructor
public class ComplianceController {

    private final ComplianceService service;

    @PutMapping("/{id}")
    public ComplianceRecord update(@PathVariable UUID id,
                                   @RequestBody ComplianceRecord updated) {
        return service.updateRecord(id, updated);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable UUID id) {
        service.softDelete(id);
        return "Deleted";
    }
}