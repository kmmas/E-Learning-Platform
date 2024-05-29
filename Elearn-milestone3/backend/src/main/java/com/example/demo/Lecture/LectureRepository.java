package com.example.demo.Lecture;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LectureRepository extends JpaRepository<Lecture, LectureId> {
    List<Lecture> findByCourse_CourseCode(String cid);
}
