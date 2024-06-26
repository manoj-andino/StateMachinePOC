package com.andinolabs.demo.apartmentsale;

import com.andinolabs.demo.stateengine.Transitionable;
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
@Table(name = "apartment")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Apartment implements Transitionable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    private Long floor;

    @Enumerated(EnumType.STRING)
    private ApartmentSaleStatus status;

    @Override
    public String getState() {
        return this.status.name();
    }

    @Override
    public void updateState(String newState) {
        this.status = ApartmentSaleStatus.valueOf(newState);
    }

}
