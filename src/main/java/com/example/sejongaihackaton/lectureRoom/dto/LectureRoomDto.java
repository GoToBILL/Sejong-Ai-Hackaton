package com.example.sejongaihackaton.lectureRoom.dto;

import com.example.sejongaihackaton.devices.dto.DeviceResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LectureRoomDto {
    private String roomNumber; // 강의실 번호
    private String floor;
    private String buildingName;
    private List<DeviceResponseDto> devices; // 디바이스 목록
}
