package com.andinolabs.demo.stateengine;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@jakarta.persistence.Entity
@Table(name = "entity")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Entity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @OneToMany(mappedBy = "entity")
    private List<EntityStateTransition> stateTransitions;

}
