import React from 'react'
//import "./Header.css"
import { Link } from 'react-router-dom';
const AdminNav = () => {
    return (
        <nav class="navbar navbar-expand-lg navbar-dark fixed-top py-0 navbar-cont">
            <h2><Link to={'/elearning'} class="navbar-brand">ELearning Platform</Link></h2>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNavDropdown">
            <ul class="navbar-nav">
                    <li class="nav-item active">
                        <a class="nav-link" href="/elearning">Home<span class="sr-only">(current)</span></a>
                    </li>
                    
                    <li class="nav-item">
                        <a to={'/signup'} class="nav-link" href='/' >logout</a>
                    </li>
                    <li class="nav-item">
                        <a to={'/signup'} class="nav-link" href='/signup' >Register</a>
                    </li>
        </ul>
    </div>
    
        </nav>
    )
}

export default AdminNav
