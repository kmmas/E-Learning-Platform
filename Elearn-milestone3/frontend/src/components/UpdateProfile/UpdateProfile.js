import "./UpdateProfile.css"
import React, { useState } from 'react';
import axios from 'axios';
import {SERVER_URL} from "../../constants"
import { Link } from 'react-router-dom';
import { CookiesProvider, useCookies } from "react-cookie";
const UpdateProfile = (props) => {

    const [cookies, setCookie] = useCookies(["user"]);
    function handleUpdate(user) {
        setCookie("user", user, { path: "/" });
    }
    const [repass,setRepass] = useState('')
    const [info,setInfo] = useState({
        email: '',
        phone: '',
        school: '',
        degree: '',
        birthDate : ''
    })
    function handleChange(event){
        setInfo({...info,[event.target.name]:event.target.value})
    }
    const validateEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
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
    function validatePhone(phone) {
        if(!isNaN(phone)){
            return true
        }
        return false
    }
    const handleSubmit = (e) => {
        e.preventDefault();
        const isValidEmail = validateEmail(info.email);
        const isValidBirth = validateAge(info.birthDate);
        const isValidPhone = validatePhone(info.phone);
    
        if (!isValidEmail) {
            document.getElementById('email').placeholder="must contain @ and . as(kk@ll.dd)";
            document.getElementById('email').classList.add('PPvibrate'); // Add the vibrate class
            setTimeout(() => {
            document.getElementById('email').classList.remove('PPvibrate'); // Remove the vibrate class after a short delay
            document.getElementById('email').placeholder="Email";
            }, 1000);
        }
        if (!isValidPhone) {
            document.getElementById('phone').classList.add('PPvibrate'); // Add the vibrate class
            setTimeout(() => {
            document.getElementById('phone').classList.remove('PPvibrate'); // Remove the vibrate class after a short delay
        }, 1000);
        }
        if (!isValidBirth) {
            document.getElementById('date').classList.add('PPvibrate'); // Add the vibrate class
            setTimeout(() => {
            document.getElementById('date').classList.remove('PPvibrate'); // Remove the vibrate class after a short delay
            }, 1000);
        }
        if(isValidEmail && isValidBirth && isValidPhone){
            console.log(info);
            axios.post(SERVER_URL+'/profile',info,{
                auth: {
                    username: props.user.email,
                    password: props.user.password
                }
            }).then(response=>{
                handleUpdate({email : info.email,password : props.user.password})
            }).catch(err =>{alert(err.response.data)})
        }
        };
    return (
        <div className='update-profile-Container'>
                <form className='update-section' onSubmit={handleSubmit}>
                    <h1 className='header'>UPDATE PROFILE INFO</h1>
                    <div className='field'>
                        <label for="email">Email</label>
                        <input  type={"text"} id="email" name="email" placeholder={"Email"} onChange={handleChange}/>            
                    </div>
                    <div className='field'>
                        <label for="date">Birthday</label>
                        <input type={"date"} id= "date" name="birthDate" placeholder={"Date of Birth"} onChange={handleChange}/>            
                    </div>
                    <div className='field'>
                        <label for="phone">Phone</label>
                        <input id="phone" type="tel" name="phone"   placeholder={"Phone Number"} onChange={handleChange}/>
                    </div>
                    <div className='field'>
                        <label for="school">School</label>
                        <input type={"text"} id="school" name="school" placeholder={"School"} onChange={handleChange}/>            
                    </div>
                    <div className='field'>
                        <label for="work">Degree</label>
                        <select className='degr' placeholder='Degree'id='degree'  name="degree" required onChange={handleChange}>
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
                        </select>                    </div>
                    <input className='btn btn-dark submit-btn' type={"submit"} value={"Update Profile"}/>
                    <Link style={{textDecoration:"none"}} className="mt-2 text-light" to='/profile'>return to profile page</Link>
                </form>
        </div>
        
    )
}
export default UpdateProfile;