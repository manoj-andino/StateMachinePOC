package com.andinolabs.demo.landprocurement;

import com.andinolabs.demo.stateengine.Transitionable;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "land")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Land implements Transitionable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Double area;

    @Enumerated(EnumType.STRING)
    private LandProcurementStatus status;

    @Override
    @JsonIgnore
    public String getState() {
        return this.status.name();
    }

    @Override
    public void updateState(String newState) {
        this.status = LandProcurementStatus.valueOf(newState);
    }

}
