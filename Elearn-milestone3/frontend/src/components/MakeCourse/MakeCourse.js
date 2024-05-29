import './MakeCourse.css';
import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import {SERVER_URL} from "../../constants"
import axios from 'axios';

function MakeCourse(props) {
  const [info,setInfo] = useState({
    courseCode: '',
    courseName : '',
    description : '',
    deadLine : '',
  })
function handleChange(event){
    setInfo({...info,[event.target.name]:event.target.value})
}
async function handleSubmit(e){
    e.preventDefault();
    console.log(info);
      try{
        let email = props.user.email
        let password = props.user.password
        axios.post(SERVER_URL+'/instructor/addCourse',info,{
              auth: {
                  username: email,
                  password: password
              }
          }).then(response=>{
              alert(response.data)
          })
    }
    catch{
        alert("Errorororor");
    }
    
  };

    return (
    <div className='MCbody'>
        <nav className='MCnav'>
        <div className="text-center MClog">
            <img className='MClogo' src={require('../../images/e-learn.jpg')} alt='logo' style={{width: '100px'}} />
            <h2 className='MCh2'>E-Learning Platform</h2>
        </div>
        <div className='MClinks'>
        <a href={"/profile"} style={{textDecoration:"none"}}  className='MCbut'>Home</a>
        <a href={"/updateprofile"} style={{textDecoration:"none"}}  className='MCbut'>Edit profile</a>
        <a href={"/"} style={{textDecoration:"none"}}  className='MCbut'>Log out</a>

        </div>
        </nav>
        <div className='MCtotal'>
            <form className='MCform1' onSubmit={handleSubmit} >
                <h4 className='MCtitle'>Cousre Regsiteration</h4>
                <div className='MCform2'>
                    <div className='MClab'>
                    <label for="">Course Name: </label>
                    <input type="text" name="courseName" className='MCin' placeholder='Course Name' onChange={handleChange}  required/>
                    </div>
                    <div className='MClab'>
                    <label for="">Course Code: </label>
                    <input type="text" name="courseCode" className='MCin' placeholder='Course Code' onChange={handleChange}  required/>
                    </div>
                    <div className='MClab'>
                    <label for="">Description: </label>
                    <textarea  name="description" className='MCtextarea' placeholder='Description' onChange={handleChange}  required/>
                    </div>
                    <div className='MClab'>
                    <label for="">DeadLine: </label>
                    <input type="date" name="deadLine" className='MCin' placeholder='DeadLine' onChange={handleChange}  required/>
                    </div>
                    <button className='MCbut' type='submit' >Finish</button>
                </div>
            </form>
        </div>
    </div>

  );
}

export default MakeCourse;