package com.example.sejongaihackaton.devices.entity;

import com.example.sejongaihackaton.lectureRoom.entity.LectureRoom;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Data
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 고유 ID 자동 생성
    private Long id;

    private String deviceId;
    private String name;
    private String label;
    private String manufacturerName;
    private String presentationId;
    private String deviceManufacturerCode;
    private String locationId;
    private String ownerId;
    private String roomId;
    private String status = "off";

    @ManyToOne
    @JoinColumn(name = "lecture_room_id")
    private LectureRoom lectureRoom;
}