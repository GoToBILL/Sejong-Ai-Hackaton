package com.example.sejongaihackaton.lectureRoom.controller;
import com.example.sejongaihackaton.devices.dto.DeviceResponseDto;
import com.example.sejongaihackaton.devices.entity.Device;
import com.example.sejongaihackaton.lectureRoom.dto.LectureRoomDto;
import com.example.sejongaihackaton.lectureRoom.entity.LectureRoom;
import com.example.sejongaihackaton.lectureRoom.service.LectureRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class LectureRoomController {

    private final LectureRoomService lectureRoomService;

    // 강의실에 디바이스 추가
    @PostMapping("/{roomNumber}/devices")
    public ResponseEntity<String> addDevicesToLectureRoom(
            @PathVariable String roomNumber,
            @RequestBody List<String> deviceLabels) {
        lectureRoomService.addDevicesToRoom(roomNumber, deviceLabels);
        return ResponseEntity.ok("디바이스가 강의실에 추가되었습니다.");
    }

    // 강의실에 속한 디바이스 조회
    @GetMapping("/{roomNumber}/devices")
    public ResponseEntity<List<DeviceResponseDto>> getDevicesByRoom(@PathVariable String roomNumber) {
        List<Device> devices = lectureRoomService.getDevicesByRoomNumber(roomNumber);

        List<DeviceResponseDto> deviceDtos = devices.stream()
                .map(device -> new DeviceResponseDto(
                        device.getId(),
                        device.getLabel(),
                        device.getDeviceId(),
                        device.getName()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(deviceDtos);
    }

    // 강의실 목록 조회
    @GetMapping
    public ResponseEntity<List<List<LectureRoomDto>>> getAllLectureRoomsByFloors() {
        List<List<LectureRoomDto>> groupedRooms = lectureRoomService.getLectureRoomsGroupedByFloor();
        return ResponseEntity.ok(groupedRooms);
    }

    // 강의실 목록 조회 (status가 "on"인 디바이스만 포함)
    @GetMapping("/on-rooms")
    public ResponseEntity<List<List<LectureRoomDto>>> getAllLectureRoomsByFloorsWithStatusOn() {
        try {
            List<List<LectureRoomDto>> lectureRooms = lectureRoomService.getLectureRoomsGroupedByFloorWithStatusOn();
            return ResponseEntity.ok(lectureRooms);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}