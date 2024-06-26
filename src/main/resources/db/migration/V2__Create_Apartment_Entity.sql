CREATE TABLE apartment (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    floor BIGINT,
    status VARCHAR(255) NOT NULL
);