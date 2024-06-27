CREATE TABLE user_role (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);

INSERT INTO user_role (id, name) VALUES (GEN_RANDOM_UUID(), 'OPERATIONS');
INSERT INTO user_role (id, name) VALUES (GEN_RANDOM_UUID(), 'FINANCE');
INSERT INTO user_role (id, name) VALUES (GEN_RANDOM_UUID(), 'LEGAL');
INSERT INTO user_role (id, name) VALUES (GEN_RANDOM_UUID(), 'SALES');
INSERT INTO user_role (id, name) VALUES (GEN_RANDOM_UUID(), 'AGENTS');

CREATE TABLE entity (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE entity_state_transition (
    id UUID PRIMARY KEY,
    entity_id UUID NOT NULL,
    from_state VARCHAR(255) NOT NULL,
    to_state VARCHAR(255) NOT NULL,
    CONSTRAINT fk_entity FOREIGN KEY (entity_id) REFERENCES entity(id)
);

CREATE TABLE role_transition_mapping (
    id UUID PRIMARY KEY,
    role_id UUID NOT NULL,
    state_transition_id UUID NOT NULL,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES user_role(id),
    CONSTRAINT fk_state_transition FOREIGN KEY (state_transition_id) REFERENCES entity_state_transition(id)
);

INSERT INTO entity (id, name) VALUES (GEN_RANDOM_UUID(), 'LAND');
INSERT INTO entity (id, name) VALUES (GEN_RANDOM_UUID(), 'APARTMENT');

INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'LAND'), 'IDENTIFIED', 'LEGAL_VERIFICATION');
INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'LAND'), 'LEGAL_VERIFICATION', 'SELLER_NEGOTIATION');
INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'LAND'), 'LEGAL_VERIFICATION', 'DISMISSED');
INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'LAND'), 'SELLER_NEGOTIATION', 'DEAL_CLOSED');

INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'APARTMENT'), 'READY_FOR_SALE', 'BUYER_NEGOTIATION');
INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'APARTMENT'), 'BUYER_NEGOTIATION', 'ADVANCE_RECEIVED');
INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'APARTMENT'), 'ADVANCE_RECEIVED', 'FULL_PAYMENT_DONE');

INSERT INTO role_transition_mapping (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'OPERATIONS'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'IDENTIFIED' AND to_state = 'LEGAL_VERIFICATION'
        AND entity_id = (SELECT id FROM entity WHERE name = 'LAND')));
INSERT INTO role_transition_mapping (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'LEGAL'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'LEGAL_VERIFICATION' AND to_state = 'SELLER_NEGOTIATION'
        AND entity_id = (SELECT id FROM entity WHERE name = 'LAND')));
INSERT INTO role_transition_mapping (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'LEGAL'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'LEGAL_VERIFICATION' AND to_state = 'DISMISSED'
        AND entity_id = (SELECT id FROM entity WHERE name = 'LAND')));
INSERT INTO role_transition_mapping (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'SALES'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'SELLER_NEGOTIATION' AND to_state = 'DEAL_CLOSED'
        AND entity_id = (SELECT id FROM entity WHERE name = 'LAND')));

INSERT INTO role_transition_mapping (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'OPERATIONS'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'READY_FOR_SALE' AND to_state = 'BUYER_NEGOTIATION'
        AND entity_id = (SELECT id FROM entity WHERE name = 'APARTMENT')));
INSERT INTO role_transition_mapping (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'SALES'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'BUYER_NEGOTIATION' AND to_state = 'ADVANCE_RECEIVED'
        AND entity_id = (SELECT id FROM entity WHERE name = 'APARTMENT')));
INSERT INTO role_transition_mapping (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'SALES'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'ADVANCE_RECEIVED' AND to_state = 'FULL_PAYMENT_DONE'
        AND entity_id = (SELECT id FROM entity WHERE name = 'APARTMENT')));
INSERT INTO role_transition_mapping (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'LEGAL'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'ADVANCE_RECEIVED' AND to_state = 'FULL_PAYMENT_DONE'
        AND entity_id = (SELECT id FROM entity WHERE name = 'APARTMENT')));