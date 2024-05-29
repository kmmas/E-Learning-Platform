import React, { useState } from 'react';
import './Signup.css';
import { Link } from 'react-router-dom';
import axios from 'axios';
import {SERVER_URL} from "../../constants"
function SignUp2() {
  const [repass,setRepass] = useState('')
  const [info,setInfo] = useState({
    firstName : '',
    lastName : '',
    email: '',
    password : '',
    phone: '',
    school: '',
    degree: '',
    ssn : '',
    birthDate : '',
    student : false
  })
  function handleChange(event){
    if(event.target.name ==="student" && event.target.value === "student"){
      setInfo({...info,[event.target.name]:true})
    }
    else if(event.target.name ==="student" && event.target.value === "instructor"){
      setInfo({...info,[event.target.name]:false})
    }
    else{
      setInfo({...info,[event.target.name]:event.target.value})
    }
}
function handleRepass(event){
  setRepass(event.target.value)
}

  const validateEmail = (email) => {
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return emailRegex.test(email);
  };
  const validatePassword = (password , repassword) => {
    if(password.length < 5){
      return 0;
    }
    if(password !== repassword){
      return 1;
    }
    return 2;
  };
  function validateAge(birthdate) {
    var today = new Date();
    var selectedDate = new Date(birthdate);

    // Check if the entered date is a realistic past date
    if (selectedDate >= today) {
      return false;
    }else{
        return true
    }
  }
  function validateSSN(SSN) {
    if(!isNaN(SSN) && SSN.length === 14 ){
        return true
    }
    return false
  }
  function validatePhone(phone) {
    if(!isNaN(phone)){
        return true
    }
    return false
  }
  async function handleSubmit(e){
    e.preventDefault();
    const isValidEmail = validateEmail(info.email);
    const isValidPassword = validatePassword(info.password,repass);
    const isValidBirth = validateAge(info.birthDate);
    const isValidSSN = validateSSN(info.ssn);
    const isValidPhone = validatePhone(info.phone);

    if (!isValidEmail) {
        
        document.getElementById('email').placeholder="must contain @ and . as(kk@ll.dd)";
        document.getElementById('email').classList.add('SSvibrate'); // Add the vibrate class
        setTimeout(() => {
        document.getElementById('email').classList.remove('SSvibrate'); // Remove the vibrate class after a short delay
        document.getElementById('email').placeholder="Email";
        }, 1000);
      }
    if (isValidPassword === 0) {
    document.getElementById('password').placeholder="5 chars or more";
    document.getElementById('password').classList.add('SSvibrate'); // Add the vibrate class
    setTimeout(() => {
    document.getElementById('password').classList.remove('SSvibrate'); // Remove the vibrate class after a short delay
    document.getElementById('password').placeholder="Password";
    }, 1000);
    } 
    else if(isValidPassword === 1) {
    document.getElementById('Repassword').placeholder="Not Matched";
    document.getElementById('Repassword').classList.add('SSvibrate'); // Add the vibrate class
    setTimeout(() => {
    document.getElementById('Repassword').classList.remove('SSvibrate'); // Remove the vibrate class after a short delay
    document.getElementById('Repassword').placeholder="Re-Password";
    }, 1000);
    }
    if (!isValidSSN) {
        document.getElementById('ssn').classList.add('SSvibrate'); // Add the vibrate class
        setTimeout(() => {
        document.getElementById('ssn').classList.remove('SSvibrate'); // Remove the vibrate class after a short delay
        }, 1000);
      }
    if (!isValidPhone) {
      document.getElementById('phone').classList.add('SSvibrate'); // Add the vibrate class
      setTimeout(() => {
      document.getElementById('phone').classList.remove('SSvibrate'); // Remove the vibrate class after a short delay
      }, 1000);
    }
    if (!isValidBirth) {
        document.getElementById('birth').classList.add('SSvibrate'); // Add the vibrate class
        setTimeout(() => {
        document.getElementById('birth').classList.remove('SSvibrate'); // Remove the vibrate class after a short delay
        }, 1000);
      }
    if(isValidEmail && isValidPassword === 2 && isValidSSN && isValidBirth && isValidPhone){
    console.log(info);
          axios.post(SERVER_URL+'/register',info).then(response=>{
            alert(response.data)
            window.location.href = "http://localhost:3000/";
          }).catch(err =>{alert(err.response.data)})
  }
  };
  return (
    <div className='SSbody'>
      <div className="SSgradient-form">

      <div className='SSall'>

        <div  className="SSleftside d-flex flex-column ">
            <div className="SShead">
                <h5>Please fill the following information</h5>
            </div>
            <form action="/Login.js" method="post" className='Sff2' onSubmit={handleSubmit}>
                <div className='Sflp'>
                    <input className='Sin' placeholder='Email' name="email"  id='email' type='text' required onChange={handleChange}/>
                </div>
                <div className='Sflp'>
                <input className='Sin2' placeholder='FirstName' name="firstName"  id='fname' type='text'required onChange={handleChange}/>
                <input className='Sin2' placeholder='LastName' name="lastName"  id='lname' type='text'required onChange={handleChange}/>
                </div>
                <div className='Sflp'>
                <input className='Sin2' placeholder='Password'id='password' name="password" type='password'required onChange={handleChange}/>
                <input className='Sin2' placeholder='Re-Password'id='Repassword' type='password'required onChange={handleRepass}/>
                </div>
                <div className='Sflp'>
                <input className='Sin2' placeholder='Phone'id='phone' name='phone' type='text' onChange={handleChange}/>
                <input className='Sin2' type="date" id="birth" placeholder='Birthdate' name="birthDate" max="2030-12-31" required onChange={handleChange}/>
                </div>
                <div className='Sflp'>
                    <input className='Sin2' placeholder='SSN'id='ssn' name ="ssn" type='text'required onChange={handleChange}/>
                    <select className='Sin2' placeholder='Degree'id='degree'  name="degree" required onChange={handleChange}>
                      <option value="" disabled selected>Degree</option>
                      <option value="student">Student</option>
                      <option value="associate">Associate</option>
                      <option value="bachelor">Bachelor</option>
                      <option value="certificate">Certificate</option>
                      <option value="diploma">Diploma</option>
                      <option value="doctorate">Doctorate</option>
                      <option value="engineer">Engineer's Degree</option>
                      <option value="master">Master</option>
                      <option value="professional">Professional Degree</option>
                      <option value="specialist">Specialist Degree</option>
                      <option value="vocational">Vocational</option>
                    </select>
                </div>
                <div  className='Sflp'>
                    <input className='Sin2' id="school" placeholder='school' name="school" required onChange={handleChange}/>
                </div>
                <div  className='Sflp2'>
                  <label ><input type='radio'  id='student' value="student" name="student" required onChange={handleChange}/> Student</label>
                  <label ><input type='radio' id='instructor' value="instructor" name="student" required onChange={handleChange}/> Instructor</label>
                </div>

                <button className='Sbutt' type='submit' >Register</button>
            </form>
        </div>

        <div className="SSrightside">

            <div className="SSfirst">
                <img className='SSLOGO' src={require('../../images/e-learn.jpg')} alt='logo'/>
                <h1 style={{color:"white"}}>Sign Up</h1>
            </div>
            <div className="SSSecond">
                <h5>Welcome to Our E-Learning Platform! 🚀</h5>
                <h6 className='SSTEX'>Unlock limitless learning possibilities with us. Sign up now and embark on a journey of knowledge and growth! 📚✨</h6>
                <Link style={{textDecoration:"none"}}  className="SSback" to={"/"}>Go Back</Link>
            </div>
        </div>

      </div>

    </div>
    </div>

  );
}

export default SignUp2;