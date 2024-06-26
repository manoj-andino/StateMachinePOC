INSERT INTO entity (id, name) VALUES (GEN_RANDOM_UUID(), 'APARTMENT');

INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'APARTMENT'), 'READY_FOR_SALE', 'BUYER_NEGOTIATION');
INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'APARTMENT'), 'BUYER_NEGOTIATION', 'ADVANCE_RECEIVED');
INSERT INTO entity_state_transition (id, entity_id, from_state, to_state)
VALUES (GEN_RANDOM_UUID(), (SELECT id FROM entity WHERE name = 'APARTMENT'), 'ADVANCE_RECEIVED', 'FULL_PAYMENT_DONE');

INSERT INTO role_state_transition (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'OPERATIONS'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'READY_FOR_SALE' AND to_state = 'BUYER_NEGOTIATION'
        AND entity_id = (SELECT id FROM entity WHERE name = 'APARTMENT')));
INSERT INTO role_state_transition (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'SALES'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'BUYER_NEGOTIATION' AND to_state = 'ADVANCE_RECEIVED'
        AND entity_id = (SELECT id FROM entity WHERE name = 'APARTMENT')));
INSERT INTO role_state_transition (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'SALES'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'ADVANCE_RECEIVED' AND to_state = 'FULL_PAYMENT_DONE'
        AND entity_id = (SELECT id FROM entity WHERE name = 'APARTMENT')));
INSERT INTO role_state_transition (id, role_id, state_transition_id)
VALUES (GEN_RANDOM_UUID(),
    (SELECT id FROM user_role WHERE name = 'LEGAL'),
    (SELECT id FROM entity_state_transition WHERE from_state = 'ADVANCE_RECEIVED' AND to_state = 'FULL_PAYMENT_DONE'
        AND entity_id = (SELECT id FROM entity WHERE name = 'APARTMENT')));