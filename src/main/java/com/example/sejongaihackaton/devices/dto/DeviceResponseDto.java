package com.example.sejongaihackaton.devices.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeviceResponseDto {
    private Long id;       // 데이터베이스 ID
    private String label;  // 디바이스 라벨
    private String deviceId; // 디바이스 타입
    private String name;   // 제조사 이름
    private String status; // 디바이스 상태
}