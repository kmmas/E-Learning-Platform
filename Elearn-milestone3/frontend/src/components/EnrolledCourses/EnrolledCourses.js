import "./EnrolledCourses.css"
import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { SERVER_URL } from "../../constants"
import { Link } from 'react-router-dom';


const EnrolledCourses = (props) => {
    const [courses, setCourses] = useState([]);
    function handleCourse(course) {
        props.courseDet(course);
    }
    function deleteCourse(code) {
        axios.delete(SERVER_URL + "/instructor/deleteCourse/" + code, {
            auth: {
                username: props.user.email,
                password: props.user.password
            }
        }).then(response => {
            alert(response.data);
            getCourses();
        }
        ).catch(error => { alert(error) })
    }
    function getCourses() {
        console.log(props.user.email)
        console.log(props.user.password)
        let url = props.role === 'student' ? '/student/enrolledCourses' : '/instructor/getCourses';
        axios.get(SERVER_URL + url, {
            auth: {
                username: props.user.email,
                password: props.user.password
            }
        })
            .then(response => {
                console.log(response.data)
                setCourses(response.data)
            })
            .catch(error => {
                console.error('Error:', error);
            });
    }
    useEffect(() => {
        getCourses();
    }, []);
    function GO(){
        window.location.href = `http://localhost:3000/course`;
    }
    return (
        <div className='container userinfo' id="enrolled-courses">
            {props.role === "student" ? <section id='user'>ENROLLED COURSES</section> : <section id='user'>CREATED COURSES</section>}
            <ul>{courses.map((course, index) => (
                <li key={index} className="enr">
                    <span>Course Title: {course.courseName}</span>
                    <div>Course Code: {course.courseCode}</div>
                    <div className='btns'>
                        <button href="/course" className="btn btn-dark cbtns" style={{color:"white"}} onClick={()=> {props.FuncCourse(course); GO()}}>See course</button>
                        {props.role !== "student" ? <button className="btn btn-dark cbtns" onClick={()=>{deleteCourse(course.courseCode)}}>delete course</button> : <></>}
                    </div>
                </li>
            ))}
            </ul>

        </div>

    )
}
export default EnrolledCourses;
