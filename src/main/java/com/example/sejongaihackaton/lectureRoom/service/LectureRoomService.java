package com.example.sejongaihackaton.lectureRoom.service;

import com.example.sejongaihackaton.devices.dto.DeviceResponseDto;
import com.example.sejongaihackaton.devices.entity.Device;
import com.example.sejongaihackaton.devices.repository.DeviceRepository;
import com.example.sejongaihackaton.lectureRoom.dto.LectureRoomDto;
import com.example.sejongaihackaton.lectureRoom.entity.LectureRoom;
import com.example.sejongaihackaton.lectureRoom.repository.LectureRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LectureRoomService {

    private final LectureRoomRepository lectureRoomRepository;
    private final DeviceRepository deviceRepository;

    // 강의실에 디바이스 추가
    public void addDevicesToRoom(String roomNumber, List<String> deviceLabels) {
        // 강의실 조회
        LectureRoom lectureRoom = lectureRoomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new IllegalArgumentException("강의실을 찾을 수 없습니다: " + roomNumber));

        // 디바이스 조회 및 강의실에 추가
        List<Device> devices = deviceRepository.findByLabelIn(deviceLabels);
        for (Device device : devices) {
            device.setLectureRoom(lectureRoom); // 강의실 설정
        }

        // 디바이스 저장 및 강의실 업데이트
        deviceRepository.saveAll(devices);
        lectureRoom.setDevices(devices);
        lectureRoomRepository.save(lectureRoom);
    }

    // 강의실에 속한 디바이스 조회
    public List<Device> getDevicesByRoomNumber(String roomNumber) {
        LectureRoom lectureRoom = lectureRoomRepository.findByRoomNumber(roomNumber)
                .orElseThrow(() -> new IllegalArgumentException("강의실을 찾을 수 없습니다: " + roomNumber));
        return lectureRoom.getDevices(); // 강의실에 속한 디바이스 목록 반환
    }

    public List<List<LectureRoomDto>> getLectureRoomsGroupedByFloor() {
        // 모든 강의실 조회
        List<LectureRoom> lectureRooms = getAllLectureRooms();

        // LectureRoom -> LectureRoomDto 변환
        List<LectureRoomDto> lectureRoomDtos = lectureRooms.stream()
                .map(room -> new LectureRoomDto(
                        room.getRoomNumber(),
                        room.getFloor(),
                        room.getDevices().stream()
                                .map(device -> new DeviceResponseDto(
                                        device.getId(),
                                        device.getLabel(),
                                        device.getDeviceId(),
                                        device.getName()
                                ))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());

        // 강의실을 층별로 그룹화
        return groupLectureRoomsByFloor(lectureRoomDtos);
    }

    public List<List<LectureRoomDto>> getLectureRoomsGroupedByFloorWithStatusOn() {
        List<LectureRoom> lectureRooms = lectureRoomRepository.findAll();

        // LectureRoom -> LectureRoomDto 변환 (status가 "on"인 디바이스만 포함)
        List<LectureRoomDto> lectureRoomDtos = lectureRooms.stream()
                .map(room -> new LectureRoomDto(
                        room.getRoomNumber(),
                        room.getFloor(),
                        room.getDevices().stream()
                                .filter(device -> "on".equalsIgnoreCase(device.getStatus())) // status가 "on"인 디바이스만
                                .map(device -> new DeviceResponseDto(
                                        device.getId(),
                                        device.getLabel(),
                                        device.getDeviceId(),
                                        device.getName()
                                ))
                                .collect(Collectors.toList())
                ))
                .filter(dto -> !dto.getDevices().isEmpty()) // 디바이스가 없는 강의실은 제외
                .collect(Collectors.toList());

        // 기존 groupLectureRoomsByFloor 메서드 사용
        return groupLectureRoomsByFloor(lectureRoomDtos);
    }

    private List<List<LectureRoomDto>> groupLectureRoomsByFloor(List<LectureRoomDto> lectureRoomDtos) {
        Map<String, List<LectureRoomDto>> groupedByFloor = lectureRoomDtos.stream()
                .collect(Collectors.groupingBy(LectureRoomDto::getFloor));

        return groupedByFloor.values()
                .stream()
                .collect(Collectors.toList());
    }

    public List<LectureRoom> getAllLectureRooms() {
        return lectureRoomRepository.findAll();
    }
}