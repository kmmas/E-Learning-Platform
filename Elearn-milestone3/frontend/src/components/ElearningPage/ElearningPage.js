import React from 'react'
import "./ElearningPage.css"
const ProfilePage = (props) => {
    return (
        <div className='profile-page'>
            {props.children}
        </div>
    )
}
export default ProfilePage;