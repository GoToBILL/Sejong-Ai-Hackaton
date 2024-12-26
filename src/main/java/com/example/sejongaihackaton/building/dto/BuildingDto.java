package com.example.sejongaihackaton.building.dto;

import com.example.sejongaihackaton.lectureRoom.dto.LectureRoomDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BuildingDto {
    private String name; // 건물 이름
    private List<Long> lectureRoomIds;
}