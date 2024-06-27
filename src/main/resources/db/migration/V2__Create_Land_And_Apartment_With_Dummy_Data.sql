CREATE TABLE land (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    area DOUBLE PRECISION,
    status VARCHAR(255) NOT NULL
);

INSERT INTO land (id, name, area, status) VALUES (GEN_RANDOM_UUID(), 'Land 1', 100.0, 'IDENTIFIED');
INSERT INTO land (id, name, area, status) VALUES (GEN_RANDOM_UUID(), 'Land 2', 100.0, 'IDENTIFIED');
INSERT INTO land (id, name, area, status) VALUES (GEN_RANDOM_UUID(), 'Land 3', 100.0, 'IDENTIFIED');
INSERT INTO land (id, name, area, status) VALUES (GEN_RANDOM_UUID(), 'Land 4', 100.0, 'IDENTIFIED');
INSERT INTO land (id, name, area, status) VALUES (GEN_RANDOM_UUID(), 'Land 5', 100.0, 'IDENTIFIED');
INSERT INTO land (id, name, area, status) VALUES (GEN_RANDOM_UUID(), 'Land 6', 100.0, 'IDENTIFIED');

CREATE TABLE apartment (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    floor BIGINT,
    status VARCHAR(255) NOT NULL
);

INSERT INTO apartment (id, name, floor, status) VALUES (GEN_RANDOM_UUID(), 'Apartment 1', 1, 'READY_FOR_SALE');
INSERT INTO apartment (id, name, floor, status) VALUES (GEN_RANDOM_UUID(), 'Apartment 2', 2, 'READY_FOR_SALE');
INSERT INTO apartment (id, name, floor, status) VALUES (GEN_RANDOM_UUID(), 'Apartment 3', 3, 'READY_FOR_SALE');
INSERT INTO apartment (id, name, floor, status) VALUES (GEN_RANDOM_UUID(), 'Apartment 4', 4, 'READY_FOR_SALE');
INSERT INTO apartment (id, name, floor, status) VALUES (GEN_RANDOM_UUID(), 'Apartment 5', 5, 'READY_FOR_SALE');