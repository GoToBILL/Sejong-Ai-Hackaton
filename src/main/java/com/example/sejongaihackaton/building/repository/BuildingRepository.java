package com.example.sejongaihackaton.building.repository;

import com.example.sejongaihackaton.building.Entity.Building;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuildingRepository extends JpaRepository<Building, Long> {
}