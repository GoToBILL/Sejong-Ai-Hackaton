package com.example.sejongaihackaton.building.controller;

import com.example.sejongaihackaton.building.Entity.Building;
import com.example.sejongaihackaton.building.dto.BuildingDevicesSummaryDto;
import com.example.sejongaihackaton.building.dto.BuildingDto;
import com.example.sejongaihackaton.building.dto.BuildingResponseDto;
import com.example.sejongaihackaton.building.dto.ElectricityUsageDto;
import com.example.sejongaihackaton.building.service.BuildingService;
import com.example.sejongaihackaton.lectureRoom.dto.LectureRoomDto;
import com.example.sejongaihackaton.lectureRoom.entity.LectureRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/building")
@RequiredArgsConstructor
public class BuildingController {

    private final BuildingService buildingService;

    @PostMapping
    public BuildingResponseDto createBuilding(@RequestBody BuildingDto buildingDto) {
        return buildingService.createBuilding(buildingDto);
    }

    @PutMapping("/lecture-room/{lectureRoomId}/assign")
    public LectureRoomDto assignBuildingToLectureRoom(@PathVariable Long lectureRoomId, @RequestParam Long buildingId) {
        return buildingService.assignBuildingToLectureRoom(lectureRoomId, buildingId);
    }

    @GetMapping("/{buildingId}/lecture-rooms")
    public BuildingResponseDto getLectureRoomsByBuilding(@PathVariable Long buildingId) {
        return buildingService.getLectureRoomsByBuilding(buildingId);
    }

    @GetMapping("/{buildingId}/device-summary")
    public BuildingDevicesSummaryDto getDeviceSummaryByBuilding(@PathVariable Long buildingId) {
        return buildingService.getDeviceSummaryByBuilding(buildingId);
    }

    @GetMapping("/{buildingId}/week-electricity-usage")
    public ElectricityUsageDto getElectricityUsage(@PathVariable Long buildingId) {
        // 빌딩 ID에 따라 전기 사용량 데이터를 정의
        List<Double> electricityUsage;
        if (buildingId == 2L) {
            electricityUsage = Arrays.asList(15.4, 13.7, 13.8, 14.3, 13.5, 17.3);
        } else if (buildingId == 3L) {
            electricityUsage = Arrays.asList(21.5, 20.1, 23.8, 26.8, 24.5, 23.9);
        } else {
            throw new RuntimeException("Electricity usage data not found for buildingId: " + buildingId);
        }

        // DTO 반환
        return new ElectricityUsageDto(buildingId, electricityUsage);
    }
}
