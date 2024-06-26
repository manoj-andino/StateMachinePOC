CREATE TABLE entity (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);

INSERT INTO entity (id, name) VALUES (GEN_RANDOM_UUID(), 'LAND');

CREATE TABLE entity_state_transition (
    id UUID PRIMARY KEY,
    entity_id UUID NOT NULL,
    from_state VARCHAR(255) NOT NULL,
    to_state VARCHAR(255) NOT NULL,
    CONSTRAINT fk_entity FOREIGN KEY (entity_id) REFERENCES entity(id)
);

INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'LAND'), 'IDENTIFIED', 'LEGAL_VERIFICATION');
INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'LAND'), 'LEGAL_VERIFICATION', 'SELLER_NEGOTIATION');
INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'LAND'), 'LEGAL_VERIFICATION', 'DISMISSED');
INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'LAND'), 'SELLER_NEGOTIATION', 'DEAL_CLOSED');

CREATE TABLE user_role (
    id UUID PRIMARY KEY,
    name VARCHAR(255)
);

INSERT INTO user_role (id, name) VALUES (GEN_RANDOM_UUID(), 'OPERATIONS');
INSERT INTO user_role (id, name) VALUES (GEN_RANDOM_UUID(), 'SALES');
INSERT INTO user_role (id, name) VALUES (GEN_RANDOM_UUID(), 'LEGAL');

CREATE TABLE role_state_transition (
    id UUID PRIMARY KEY,
    role_id UUID NOT NULL,
    state_transition_id UUID NOT NULL,
    CONSTRAINT fk_role FOREIGN KEY (role_id) REFERENCES user_role(id),
    CONSTRAINT fk_state_transition FOREIGN KEY (state_transition_id) REFERENCES entity_state_transition(id)
);

INSERT INTO role_state_transition (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'OPERATIONS'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'IDENTIFIED' AND to_state = 'LEGAL_VERIFICATION'));
INSERT INTO role_state_transition (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'LEGAL'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'LEGAL_VERIFICATION' AND to_state = 'SELLER_NEGOTIATION'));
INSERT INTO role_state_transition (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'LEGAL'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'LEGAL_VERIFICATION' AND to_state = 'DISMISSED'));
INSERT INTO role_state_transition (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'SALES'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'SELLER_NEGOTIATION' AND to_state = 'DEAL_CLOSED'));