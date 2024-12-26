package com.example.sejongaihackaton.lectureRoom.repository;

import com.example.sejongaihackaton.lectureRoom.entity.LectureRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LectureRoomRepository extends JpaRepository<LectureRoom, Long> {
    Optional<LectureRoom> findByRoomNumber(String roomNumber);
}
