CREATE TABLE requests (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    software_name VARCHAR(100) NOT NULL,
    access_type VARCHAR(20) NOT NULL,
    reason TEXT,
    status VARCHAR(20) DEFAULT 'Pending',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
