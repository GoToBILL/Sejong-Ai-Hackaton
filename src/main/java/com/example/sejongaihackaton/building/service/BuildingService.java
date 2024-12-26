package com.example.sejongaihackaton.building.service;

import com.example.sejongaihackaton.building.Entity.Building;
import com.example.sejongaihackaton.building.dto.BuildingDevicesSummaryDto;
import com.example.sejongaihackaton.building.dto.BuildingDto;
import com.example.sejongaihackaton.building.dto.BuildingResponseDto;
import com.example.sejongaihackaton.building.repository.BuildingRepository;
import com.example.sejongaihackaton.devices.dto.DeviceResponseDto;
import com.example.sejongaihackaton.devices.entity.Device;
import com.example.sejongaihackaton.devices.entity.LabelType;
import com.example.sejongaihackaton.lectureRoom.dto.LectureRoomDto;
import com.example.sejongaihackaton.lectureRoom.entity.LectureRoom;
import com.example.sejongaihackaton.lectureRoom.repository.LectureRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BuildingService {

    private final BuildingRepository buildingRepository;
    private final LectureRoomRepository lectureRoomRepository;

    public BuildingResponseDto createBuilding(BuildingDto buildingDto) {
        Building building = new Building();
        building.setName(buildingDto.getName());

        // LectureRoom 설정
        List<LectureRoom> lectureRooms = lectureRoomRepository.findAllById(buildingDto.getLectureRoomIds());
        for (LectureRoom room : lectureRooms) {
            room.setBuilding(building);
        }
        building.setLectureRooms(lectureRooms);

        Building savedBuilding = buildingRepository.save(building);
        return toBuildingResponseDto(savedBuilding);
    }

    public LectureRoomDto assignBuildingToLectureRoom(Long lectureRoomId, Long buildingId) {
        LectureRoom lectureRoom = lectureRoomRepository.findById(lectureRoomId)
                .orElseThrow(() -> new RuntimeException("LectureRoom not found"));
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found"));

        lectureRoom.setBuilding(building);
        lectureRoomRepository.save(lectureRoom);

        return toLectureRoomDto(lectureRoom);
    }

    public BuildingResponseDto getLectureRoomsByBuilding(Long buildingId) {
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found"));
        return toBuildingResponseDto(building);
    }

    public String getBuildingByLectureRoom(Long lectureRoomId) {
        LectureRoom lectureRoom = lectureRoomRepository.findById(lectureRoomId)
                .orElseThrow(() -> new RuntimeException("LectureRoom not found"));
        return lectureRoom.getBuilding().getName();
    }

    private BuildingResponseDto toBuildingResponseDto(Building building) {
        List<LectureRoomDto> lectureRoomDtos = building.getLectureRooms().stream()
                .map(this::toLectureRoomDto)
                .toList();

        return new BuildingResponseDto(
                building.getName(),
                lectureRoomDtos
        );
    }

    private LectureRoomDto toLectureRoomDto(LectureRoom lectureRoom) {
        return new LectureRoomDto(
                lectureRoom.getRoomNumber(),
                lectureRoom.getFloor(),
                lectureRoom.getBuilding().getName(),
                lectureRoom.getDevices().stream()
                        .map(device -> new DeviceResponseDto(
                                device.getId(),
                                device.getLabel(),
                                device.getDeviceId(),
                                device.getName(),
                                device.getStatus(),
                                device.getLabelType()
                        ))
                        .toList()
        );
    }

    public BuildingDevicesSummaryDto getDeviceSummaryByBuilding(Long buildingId) {
        // 빌딩 조회
        Building building = buildingRepository.findById(buildingId)
                .orElseThrow(() -> new RuntimeException("Building not found"));

        // 빌딩에 포함된 강의실 가져오기
        List<LectureRoom> lectureRooms = building.getLectureRooms();

        // 각 강의실의 장치 목록을 Stream으로 처리
        long lightCount = lectureRooms.stream()
                .flatMap(room -> room.getDevices().stream()) // 강의실의 모든 장치를 Stream으로 변환
                .filter(device -> "on".equalsIgnoreCase(device.getStatus())) // status가 "on"인 경우만 필터링
                .filter(device -> device.getLabelType() == LabelType.LIGHT) // labelType이 LIGHT인 경우만 필터링
                .count(); // LIGHT의 개수 계산

        long doorCount = lectureRooms.stream()
                .flatMap(room -> room.getDevices().stream()) // 강의실의 모든 장치를 Stream으로 변환
                .filter(device -> "on".equalsIgnoreCase(device.getStatus())) // status가 "on"인 경우만 필터링
                .filter(device -> device.getLabelType() == LabelType.DOOR) // labelType이 DOOR인 경우만 필터링
                .count(); // DOOR의 개수 계산

        // DTO 생성 후 반환
        return new BuildingDevicesSummaryDto(building.getName(), (int) lightCount, (int) doorCount);
    }
}

