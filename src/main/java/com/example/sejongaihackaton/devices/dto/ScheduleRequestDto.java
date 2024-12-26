package com.example.sejongaihackaton.devices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleRequestDto {
    private String action;
    private int minute;
    private String deviceId;
}