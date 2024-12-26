package com.example.sejongaihackaton.devices.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeviceCommandRequest {
    private String deviceId;
    private String command;
}