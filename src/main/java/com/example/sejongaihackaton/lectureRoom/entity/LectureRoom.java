package com.example.sejongaihackaton.lectureRoom.entity;

import com.example.sejongaihackaton.building.Entity.Building;
import com.example.sejongaihackaton.devices.entity.Device;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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

    @ManyToOne
    @JoinColumn(name = "building_id", nullable = false)
    @JsonBackReference
    private Building building; // 소속된 건물

    @OneToMany(mappedBy = "lectureRoom", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Device> devices; // 강의실에 포함된 디바이스 목록
}
