import './CourseEnroll.css';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { SERVER_URL } from "../../constants"

function CourseEnroll(props) {
  const [studentData, setStudentData] = useState()
  const [courses, setCourses] = useState([]);
  const [info, setInfo] = useState({
    courseName: '',
  })
  function handleChange(event) {
    setInfo({ ...info, [event.target.name]: event.target.value })
  }
  async function handleSubmit(e) {
    e.preventDefault();
    console.log(info);

    myFunction();

  };
  function myFunction() {
    axios.post(SERVER_URL + '/student/availableCourses', info, {
      auth: {
        username: props.user.email,
        password: props.user.password
      }
    }).then(response => {
      console.log(response.data)
      setCourses(response.data)
    }).catch(error => {
      alert(error.response.data);
    });
  }

  function Enroll(cId) {
    let email = props.user.email;
    let password = props.user.password;
    axios.put(SERVER_URL + `/student/enroll/${cId}`, {}, {
      auth: {
        username: email,
        password: password,
      }
    })
      .then(response => {
        alert(response.data);
        myFunction();
      })
      .catch(error => {
        alert(error.response.data);
      });
  }
  useEffect(() => { myFunction() }, []);
  window.onload = function () {
    myFunction();
    console.log("testtt")
  };
  return (
    <div className='ECbody'>
      <nav className='ECnav'>
        <div className="text-center EClog">
          <img className='EClogo' src={require('../../images/e-learn.jpg')} alt='logo' style={{ width: '100px' }} />
          <h2 className='ECh2'>E-Learning Platform</h2>
        </div>
        <div className='EClinks'>
          <a href={"/profile"} style={{ textDecoration: "none" }} className='ECbut'>Home</a>
          <a href={"/updateprofile"} style={{ textDecoration: "none" }} className='ECbut'>Edit profile</a>
          <a href={"/"} style={{ textDecoration: "none" }} className='ECbut'>Log out</a>

        </div>
      </nav>
      <div className='ECtotal'>
        <div className="ECtab ">
          <div className='ECname'>
            <h6 className='ECname2'>Available Courses</h6>
          </div>
          <form className='ECsearcgbar' onSubmit={handleSubmit}>
            <input type="text" placeholder="Course Name" onChange={handleChange} className='ECsearch' name="courseName" />
            {/* <input type="text" placeholder="Instructor Name" onChange={handleChange} className='ECsearch' name="instructorName"/> */}
            <button type="submit" className='ECSebut'><img className='ECsearchlogo' src={require('../../images/icons8-search-50.png')} alt='searchlogo' style={{ width: '25px' }} /> Search</button>
            {/* <select className='ECSebut' placeholder='None'id='filter'  name="filtertype" onChange={handleChange} required>
                    <option value="" disabled selected>None</option>
                    <option value="CousreName">1-CousreName</option>
                    <option value="InstructorName">2-InstructorName</option>
                    <option value="1and2">1and2</option>
                    <option value="1or2">1or2</option>
            </select> */}

          </form>
        </div>
        <div className='ECdata'>
          <div id="Courses" className="ECtabcontent" >
            {courses.map((course, index) => (
              <div key={index} className='ECCourses'>
                <div className='ECtitle'>
                  <Link to="/course" className='Clink' rel="noopener noreferrer">Course Title: {course.courseName}</Link>
                  <div className='ECreg'>
                    <button className='ECTeacBut1' onClick={() => Enroll(course.courseCode)} >Enroll</button>
                  </div>
                </div>
                <h6 className='ECh6'>Instructor firstName: {course.instructorFirstName}</h6>
                <h6 className='ECh6'>Instructor lastName: {course.instructorLastName}</h6>
                <h6 className='ECh6'>Course Code: {course.courseCode}</h6>

                <h6 className='ECh6'>Description:</h6>
                <p>{course.description}</p>
                <h6 className='ECh6'>DeadLine: {
                  new Intl.DateTimeFormat('en-US', {
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric',
                  }).format(new Date(course.deadLine))
                }</h6>
              </div>
            ))}


          </div>

        </div>
      </div>
    </div>

  );
}

export default CourseEnroll;