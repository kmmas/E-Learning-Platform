import React from 'react'
import "./SideBar.css"
import { FaHome,FaUnlockAlt,FaLock   } from "react-icons/fa";
import { MdDashboard } from "react-icons/md";
import logo from "../../images/learninglogo.jpg"
import { Link } from 'react-router-dom';
const SideBar = () => {
    return (
        <div class="wrapper">
            
            <div class="sidebar">
                <div className="system-image">
                    <img src={logo} alt="logo" id="logo"/>
                    {/* <h2>E-Learning Platform</h2> */}
                </div>
                <ul>
                    <li>
                        <a href="index.html" class="active" >
                            <span class="icon"><FaHome fontSize={"20px"}/></span>
                            <span class="item">Home</span>
                        </a>
                    </li>
                    <li>
                        <a href="index.html#section1">
                            <span class="icon"><MdDashboard fontSize={"20px"}/></span>
                            <span class="item">Dashboard</span>
                        </a>
                    </li>
                    <li>
                        <a href="TopStudents.html">
                            <span class="icon"><FaUnlockAlt  hboard fontSize={"20px"}/></span>
                            <span class="item">People</span>
                        </a>
                    </li>
                    <li>
                        <a href="DoTheMath.html">
                            <span class="icon"><FaLock  fontSize={"20px"}/></span>
                            <span class="item">Revision videos</span>
                        </a>
                    </li>
                    <li>
                        <a href="video.php">
                            <span class="icon"><MdDashboard fontSize={"20px"}/></span>
                            <span class="item">Semester videos</span>
                        </a>
                    </li>
                    <li>
                        <Link to="/signup" href="/">
                            <span class="icon"><MdDashboard fontSize={"20px"}/></span>
                            <span class="item">Sign up</span>
                        </Link>
                    </li>
                    
                </ul>
            </div>
        </div>
    )
}
export default SideBar;
