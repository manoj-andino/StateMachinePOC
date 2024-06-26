package com.andinolabs.demo.landprocurement;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LandRepository extends JpaRepository<Land, UUID> {
}
