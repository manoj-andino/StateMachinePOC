package com.andinolabs.demo.stateengine;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "entity_state_transition")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EntityStateTransition {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "entity_id", referencedColumnName = "id")
    private com.andinolabs.demo.stateengine.Entity entity;

    private String fromState;

    private String toState;

    @ManyToMany
    @JoinTable(
            name = "role_transition_mapping",
            joinColumns = @JoinColumn(name = "state_transition_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<UserRole> roles;

}
