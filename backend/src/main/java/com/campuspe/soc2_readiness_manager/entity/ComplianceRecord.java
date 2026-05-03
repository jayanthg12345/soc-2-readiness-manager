package com.campuspe.soc2_readiness_manager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "compliance_record")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplianceRecord {

    @Id
    private UUID id;

    private String title;
    private String description;
    private String status;
    private int score;

    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}