-- V1__init.sql

CREATE TABLE compliance_record (
                                   id UUID PRIMARY KEY,
                                   title VARCHAR(255) NOT NULL,
                                   description TEXT,
                                   status VARCHAR(50) NOT NULL,
                                   score INT,

                                   created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                   updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

                                   deleted BOOLEAN DEFAULT FALSE
);

-- Indexes (important for performance)
CREATE INDEX idx_compliance_status ON compliance_record(status);
CREATE INDEX idx_compliance_created_at ON compliance_record(created_at);