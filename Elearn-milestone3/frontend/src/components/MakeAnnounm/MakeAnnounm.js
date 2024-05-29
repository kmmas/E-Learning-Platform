import './MakeAnnounm.css';
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { SERVER_URL } from "../../constants"
import axios from 'axios';

function MakeAnnounm(props) {
    const [info, setInfo] = useState({
        announcementName: '',
        description: '',
        courseCode: props.course.courseCode
    })


    function handleChange(event) {
        setInfo({ ...info, [event.target.name]: event.target.value })
    }
    async function handleSubmit(e) {
        e.preventDefault();
        console.log(info);
        try {
            let email = props.user.email
            let password = props.user.password
            axios.post(SERVER_URL + '/announce/addAnnounce', info, {
                auth: {
                    username: email,
                    password: password
                }
            }).then(response => {
                alert(response.data)
            }).catch(err => {
                alert(err.response.data)
              })
        }
        catch {
            alert("Error");
        }

    };

    return (
        <div className='MCbody'>
            <nav className='MCnav'>
                <div className="text-center MClog">
                    <img className='MClogo' src={require('../../images/e-learn.jpg')} alt='logo' style={{ width: '100px' }} />
                    <h2 className='MCh2'>E-Learning Platform</h2>
                </div>
                <div className='MClinks'>
                    <a href={"/profile"} style={{ textDecoration: "none" }} className='MCbut'>Home</a>
                    <a href={"/courseenroll"} style={{ textDecoration: "none" }} className='MCbut'>Available Courses</a>
                    <a href={"/updateprofile"} style={{ textDecoration: "none" }} className='MCbut'>Edit profile</a>
                    <a href={"/"} style={{ textDecoration: "none" }} className='MCbut'>Log out</a>

                </div>
            </nav>
            <div className='MCtotal'>
                <form action='/course' className='MCform1' onSubmit={handleSubmit} >
                    <h4 className='MCtitle'>Announcement Form</h4>
                    <div className='MCform2'>
                        <div className='MClab'>
                            <label for="">Announcement Title:</label>
                            <input type="text" id="announcementName" className='MCin' name="announcementName" onChange={handleChange} required />
                        </div>
                        <div className='MClab'>
                            <label for="">Description: </label>
                            <textarea name="description" className='MCtextarea' placeholder='' onChange={handleChange} required />
                        </div>
                        <button className='MCbut' type='submit' >Done</button>
                    </div>
                </form>
            </div>
        </div>

    );
}

export default MakeAnnounm;