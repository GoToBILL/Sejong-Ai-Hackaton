package com.example.sejongaihackaton.building.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ElectricityUsageDto {
    private Long buildingId;
    private List<Double> electricityUsage; // 전기 사용량 리스트
}