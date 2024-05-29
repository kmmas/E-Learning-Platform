package com.example.demo.course;

import com.example.demo.student.Student;
import com.example.demo.student.StudentRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private EntityManager entityManager;

    public Course saveCourseDetails(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(String courseCode) {
        return courseRepository.findById(courseCode).orElse(null);
    }

    public ResponseEntity<String> deleteCourse(Course course) {
        courseRepository.deleteById(course.getCourseCode());
        return ResponseEntity.status(200).body("deleted Successfully the course ");
    }

    public List<Course> sortBy(String criteria) {
        Metamodel metamodel = entityManager.getMetamodel();
        EntityType<Course> courseEntityType = metamodel.entity(Course.class);
        Set<String> attributeNames = courseEntityType.getAttributes()
                                                     .stream()
                                                     .map(Attribute::getName)
                                                     .collect(java.util.stream.Collectors.toSet());
        if (!attributeNames.contains(criteria)) {
            return null;
        }

        String jpqlQuery = "SELECT c FROM Course c ORDER BY c." + criteria;
        TypedQuery<Course> query = entityManager.createQuery(jpqlQuery, Course.class);
        return query.getResultList();

    }

    public ResponseEntity<String> enrollCourse(String courseCode, Student student) {
        Set<Course> courseSet = null;
        Course course = null;

        /// get the course with specified course code
        if (courseRepository.findById(courseCode).isPresent()) course = courseRepository.findById(courseCode).get();
        else {
            return ResponseEntity.status(409).body("the course doesn't exist");
        }

        ///comparing deadlines
        LocalDateTime localDateTime = LocalDateTime.now();
        Date date = java.sql.Timestamp.valueOf(localDateTime);
        int comparisonResult = course.getDeadLine().compareTo(date);
        if (comparisonResult < 0) {
            return ResponseEntity.status(406).body("Your date is in the past.");
        }


        //fetch the set of course the student is enrolled, then add the new course
        courseSet = student.getEnrolledCourses();
        if (courseSet.contains(course)) {
            return ResponseEntity.status(400).body("Already Enrolled");
        }
        courseSet.add(course);
        student.setEnrolledCourses(courseSet);
        studentRepository.save(student);
        return ResponseEntity.status(200).body("enrolled successfully");
    }

    public Specification<Course> filterCourses(long studentId, boolean enrolled) {
        return (root, cq, cb) -> {
            Subquery<Course> sq = cq.subquery(Course.class);
            Root<Course> c2 = sq.from(Course.class);
            Join<Course, Student> x = c2.join("studentSet");
            sq.where(cb.and(cb.equal(x.get("id"), studentId), cb.equal(c2.get("courseCode"), root.get("courseCode"))));
            if (enrolled) {
                return cb.exists(sq);
            } else {
                return cb.exists(sq).not();
            }
        };
    }

    public Page<Course> getAvailableCourses(long studentId, String courseName) {
        Specification<Course> spec;
        if (courseName != null && !courseName.isEmpty()) {
            spec = filterCourses(studentId, false).and((root, cq, cb) -> cb.like(root.get("courseName"),
                                                                                 "%" + courseName + "%"));
        } else {
            spec = filterCourses(studentId, false);
        }
        return courseRepository.findAll(spec, PageRequest.of(0, 20));
    }

    public Page<Course> getEnrolledCourses(long studentId) {
        return courseRepository.findAll(filterCourses(studentId, true), PageRequest.of(0, 20));
    }

}
