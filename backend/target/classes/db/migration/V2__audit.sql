-- V2__audit.sql

CREATE TABLE audit_log (
                           id UUID PRIMARY KEY,

                           entity_type VARCHAR(100) NOT NULL,
                           entity_id UUID NOT NULL,

                           action VARCHAR(50) NOT NULL,  -- CREATE, UPDATE, DELETE

                           old_value TEXT,
                           new_value TEXT,

                           performed_by VARCHAR(100),

                           created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Composite index (VERY IMPORTANT)
CREATE INDEX idx_audit_entity
    ON audit_log(entity_type, entity_id);

-- Additional indexes for performance
CREATE INDEX idx_audit_action
    ON audit_log(action);

CREATE INDEX idx_audit_created_at
    ON audit_log(created_at);