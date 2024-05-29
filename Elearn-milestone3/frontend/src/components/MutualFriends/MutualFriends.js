import React from 'react'
import "./MutualFriends.css"
const MutualFriends = () => {
    return (
        <div className='userinfo' id="mutual">
            <section id='user'>PEOPLE ENROLLED ON THE SAME COURSES</section>
            <ul>
                <li className='mutualPeople-info'><span className='mutual-name'>Mohamed Aly</span> <span className="course">Database Course</span></li>
                <li className='mutualPeople-info'><span className='mutual-name'>Mahmoud Ibrahim</span> <span className="course">Numerical Course</span></li>
                <li className='mutualPeople-info'><span className='mutual-name'>Ziad Motaz</span> <span className="course">Software engineering Course</span></li>
            </ul>
        </div>
    )
}
export default MutualFriends;
