package com.example.sejongaihackaton.building.Entity;

import com.example.sejongaihackaton.lectureRoom.entity.LectureRoom;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Building {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "building_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "building", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<LectureRoom> lectureRooms;
}