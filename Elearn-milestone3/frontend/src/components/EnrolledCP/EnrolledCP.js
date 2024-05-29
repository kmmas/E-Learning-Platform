import React from 'react'
import "./EnrolledCP.css"
const EnrolledCP = (props) => {
    return (
        <div className='user-info-container'>
            {props.children}
        </div>
    )
}
export default EnrolledCP;
