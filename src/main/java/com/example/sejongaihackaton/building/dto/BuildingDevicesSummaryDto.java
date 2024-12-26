package com.example.sejongaihackaton.building.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDevicesSummaryDto {
    private String buildingName;
    private int lightCount; // "LIGHT" 장치의 개수
    private int doorCount;  // "DOOR" 장치의 개수
}