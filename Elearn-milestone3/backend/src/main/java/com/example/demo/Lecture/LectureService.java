package com.example.demo.Lecture;


import com.example.demo.course.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LectureService {
    @Autowired
    LectureRepository lr;

    public void createLecture(recieveLectureDTO info, Course course) {
        Lecture lecture = new Lecture(course,
                                      info.getVideoLink(),
                                      info.getTitle(),
                                      info.getDescription(),
                                      new java.sql.Date(System.currentTimeMillis()));
        lr.save(lecture);


    }

    public boolean deleteLecture(long lid, Course course) {
        LectureId id = new LectureId();
        id.setId(lid);
        id.setCourse(course);


        if (!lr.existsById(id)) {
            return false;
        } else {
            lr.deleteById(id);
            return true;


        }


    }

    public List<sendLectureDTO> getAllLectures(String cid) {
        List<Lecture> list = lr.findByCourse_CourseCode(cid);
        List<sendLectureDTO> newlist = new ArrayList<>();
        for (Lecture i : list) {
            sendLectureDTO entry = new sendLectureDTO();
            entry.setCourseCode(cid);
            entry.setTitle(i.getTitle());
            entry.setDescription(i.getDescription());
            entry.setVideoLink(i.getVideoLink());
            entry.setLid(i.getId());

            newlist.add(entry);
        }
        return newlist;
    }

}
