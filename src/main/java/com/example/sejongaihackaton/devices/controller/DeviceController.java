package com.example.sejongaihackaton.devices.controller;

import com.example.sejongaihackaton.devices.dto.DeviceCommandRequest;
import com.example.sejongaihackaton.devices.dto.DeviceResponseDto;
import com.example.sejongaihackaton.devices.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/devices/commands")
    public ResponseEntity<String> sendCommandToDevice(@RequestBody DeviceCommandRequest commandRequest) {
        try {
            String result = deviceService.sendCommandToDevice(commandRequest);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("명령 전송 실패: " + e.getMessage());
        }
    }

    @GetMapping("/devices")
    public ResponseEntity<List<DeviceResponseDto>> getAllDevices() {
        try {
            List<DeviceResponseDto> devices = deviceService.getAllDevices();
            return ResponseEntity.ok(devices);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(List.of());
        }
    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<DeviceResponseDto> getDeviceById(@PathVariable Long id) {
        try {
            DeviceResponseDto device = deviceService.getDeviceById(id);
            return ResponseEntity.ok(device);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}
