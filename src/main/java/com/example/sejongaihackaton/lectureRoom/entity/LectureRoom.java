package com.example.sejongaihackaton.lectureRoom.entity;

import com.example.sejongaihackaton.devices.entity.Device;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class LectureRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_room_id")
    private Long id;

    @Column(nullable = false)
    private String floor; // 층 정보 추가

    @Column(nullable = false, unique = true)
    private String roomNumber; // 강의실 번호

    @OneToMany(mappedBy = "lectureRoom", cascade = CascadeType.ALL)
    private List<Device> devices; // 강의실에 포함된 디바이스 목록
}
