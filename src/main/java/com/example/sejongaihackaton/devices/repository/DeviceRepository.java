package com.example.sejongaihackaton.devices.repository;


import com.example.sejongaihackaton.devices.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByLabelIn(List<String> labels);
    Optional<Device> findByDeviceId(String deviceId);
    Optional<Device> findByLabel(String label);
}
