import './Course.css';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import { SERVER_URL } from "../../constants"

function Course(props) {
  var teacher = props.user.page;
  console.log(props.course)
  function trigger(evt, cityName) {
    var i, tabcontent, tablinks, cityElement;
    cityElement = document.getElementById(cityName);
    tabcontent = document.getElementsByClassName("Ctabcontent");
    for (i = 0; i < tabcontent.length; i++) {
      tabcontent[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablinks");
    for (i = 0; i < tablinks.length; i++) {
      tablinks[i].className = tablinks[i].className.replace(" active", "");
    }
    // Set the display property to "flex"
    cityElement.style.display = "flex";
    cityElement.style.flexDirection = "column";
    cityElement.style.justifyContent = "space-between";
    // cityElement.style.alignItems = "center";
    evt.currentTarget.className += " active";
    if (teacher == 2 || teacher == 3) {
      // Get elements with the class name "CTeacBut2" (returns a collection)
      var elements = document.getElementsByClassName("CTeacBut2");
    
      // Loop through the collection and set the display style for each element
      for (var i = 0; i < elements.length; i++) {
        elements[i].style.display = "flex";
      }
    }else{
            // Get elements with the class name "CTeacBut2" (returns a collection)
            var elements = document.getElementsByClassName("CTeacBut2");
    
            // Loop through the collection and set the display style for each element
            for (var i = 0; i < elements.length; i++) {
              elements[i].style.display = "none";
            }
    }
  }
  const [Lecture, setLectures] = useState([]);
  const [Anouncement, setAnouncements] = useState([]);
   function DeleteLec(id) {
    // e.preventDefault();
    console.log(id);
    try {
      let email = props.user.email
      let password = props.user.password
      axios.post(SERVER_URL + '/course/deleteLecture', id, {
        auth: {
          username: email,
          password: password
        }
      }).then(response => {
        setLectures(response.data);
      })
    }
    catch {
      alert("Error");
    }

  };
   function DeleteAnno(name) {
    // e.preventDefault();
    console.log(name);
    try {
      let email = props.user.email
      let password = props.user.password
      axios.post(SERVER_URL + '/announce/deleteAnnounce', name, {
        auth: {
          username: email,
          password: password
        }
      }).then(response => {
        setAnouncements(response.data);
      })
    }
    catch {
      alert("Error");
    }

  };


////////////////////////////////////get Lecture/////////////////////////////////////
useEffect(() => {
    console.log(props.user.email)
    console.log(props.user.password)
    axios.get(SERVER_URL + `/lecture/getLectures/${props.course.courseCode}`)
        .then(response => {
            console.log(response.data)
            setLectures(response.data);
        })
        .catch(error => {
            console.error('Error:', error);
        });
    },[]);
  ////////////////////////////////////get Announcement/////////////////////////////////////
useEffect(() => {
    axios.get(SERVER_URL + `/announce/getAllAnnounce/${props.course.courseCode}`)
        .then(response => {
            console.log(response.data)
            setAnouncements(response.data);
            console.log(Anouncement)
        })
        .catch(error => {
            console.error('Error:', error);
        });
    },[]);
  return (
    <div className='Cbody' >
      <nav className='Cnav'>
        <div className="text-center Clog">
          <img className='Clogo' src={require('../../images/e-learn.jpg')} alt='logo' style={{ width: '100px' }} />
          <h2 className='Ch2'>E-Learning Platform</h2>

        </div>
        <div className='Clinks'>
          <a href={"/profile"} style={{ textDecoration: "none" }} className='ECbut'>Home</a>
          <a href={"/courseenroll"} style={{ textDecoration: "none" }} className='ECbut'>Available Courses</a>
          <a href={"/updateprofile"} style={{ textDecoration: "none" }} className='ECbut'>Edit profile</a>
          <a href={"/"} style={{ textDecoration: "none" }} className='ECbut'>Log out</a>

        </div>
      </nav>
      <div className='Ctotal'>
        <div className="Ctab ">
          <button className="tablinks" onClick={(event) => trigger(event, 'Details')}>Details</button>
          <button className="tablinks" onClick={(event) => trigger(event, 'Lectures')}>Lectures</button>
          <button className="tablinks" onClick={(event) => trigger(event, 'Announcement')}>Announcement</button>
        </div>
        <div className='Cdata'>

          <div id="Details" style={{ color: "white" }} className="Ctabcontent ">
            <div className='Cdetails'>
              <header className='Cheader'>
                <h1 className='Cht'>{props.course.courseName}</h1>
              </header>

              <section className='Cheader2'>
                <h3 className='Ch1'>Course Description</h3>
                <h5 className='Ch5'>{props.course.description}</h5>
              </section >
              <section className='Cheader'>
                <h3 className='Ch1'>DeadLine</h3>
                <h5>{
                  new Intl.DateTimeFormat('en-US', {
                    year: 'numeric',
                    month: 'long',
                    day: 'numeric',
                  }).format(new Date(props.course.deadLine))
                }</h5>
              </section>
            </div>
          </div>

          <div id="Lectures" className="Ctabcontent">
            <div className='CAdd'>
              <a href='/makelecture' style={{textDecoration:"none"}} className='CTeacBut2'><span style={{marginRight:"8px"}} class="plus-icon">&#43;</span> Add Lecture</a>
            </div>
            {Lecture.map((lecture, index) => (
              <div key={index} className='CLecture1'>
                <div className='Ctitle'>
                  <a href={lecture.videoLink} target="_blank" className='Clink' rel="noopener noreferrer">Lecture title: {lecture.title}</a>
                  <button className='CTeacBut' onClick={() => DeleteLec(lecture.id)}>Delete</button>
                </div>
                <h6 className='Ch6'>Description:</h6>
                <p>{lecture.description}</p>
              </div>
            ))}
          </div>

          <div id="Announcement" className="Ctabcontent">
            <div className='CAdd'>
              <a href='/makeannoun' style={{textDecoration:"none"}} className='CTeacBut2'><span style={{marginRight:"8px"}} class="plus-icon">&#43;</span> Add Announcement</a>
            </div>
            {Anouncement.map((anouncement, index) => (
              <div key={index} className='CLecture1'>
                <div className='Ctitle'>
                  <h2 target="_blank" className='Clink'>Announcement title: {anouncement.announcementName}</h2>
                  <button className='CTeacBut' onClick={() => DeleteAnno(anouncement.announcementName)}>Delete</button>
                </div>
                <h6 className='Ch6'>Description:</h6>
                <p>{anouncement.description}</p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>

  );
}

export default Course;




