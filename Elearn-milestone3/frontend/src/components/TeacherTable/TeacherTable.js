import React, { useEffect } from 'react'
import { GrCaretNext } from "react-icons/gr";
import { GrCaretPrevious } from "react-icons/gr";
import { useState } from 'react'
import './TeacherTable.css'
import axios from 'axios';
import { SERVER_URL } from '../../constants'

const TeacherTable = (props) => {
    const [counter, setCounter] = useState(0)
    const [teacherData, setTeacherData] = useState([])
    const [info, setInfo] = useState({
        firstName: '',
        lastName: '',
        email: '',
        phone: '',
        school: '',
        degree: '',
        ssn: '',
        birthDate: '',
        firstResult: counter,
        maxResults: 8,
        sortBy: 'firstName',
        descending: false,
        or: false,
        hasPrivilege: 0
    })
    function handleChange(event) {
        if (event.target.name === 'tagmode' && event.target.value === 'or')
            setInfo({ ...info, or: true })
        else if (event.target.name === 'tagmode' && event.target.value === 'and')
            setInfo({ ...info, or: false })

        else if (event.target.name === 'hasPrivilege' && event.target.value === 'admin')
            setInfo({ ...info, [event.target.name]: 2 })
        else if (event.target.name === 'hasPrivilege' && event.target.value === "isn't admin")
            setInfo({ ...info, [event.target.name]: 1 })
        else if (event.target.name === 'hasPrivilege' && event.target.value === "both")
            setInfo({ ...info, [event.target.name]: 0 })
        else if (event.target.name === 'order' && event.target.value === "asc")
            setInfo({ ...info, descending: false })
        else if (event.target.name === 'order' && event.target.value === "desc")
            setInfo({ ...info, descending: true })
        else
            setInfo({ ...info, [event.target.name]: event.target.value })
    }

    function handleClick() {
        console.log(info)
        axios.post(SERVER_URL + '/admin/instructors', info, {
            auth: {
                username: email,
                password: password
            }
        })
            .then(response => {

                setTeacherData(response.data)
            })
            .catch(error => {
                // Handle errors here
                alert(error)
            });
    }
    const email = props.email
    const password = props.password
    useEffect(() => {
        axios.post(SERVER_URL + '/admin/instructors', {}, {
            auth: {
                username: email,
                password: password
            }
        })
            .then(response => {
                setTeacherData(response.data)
            })
            .catch(error => {
                // Handle errors here
                console.error('Error:', error);
            });

    },[]);
    function handleNext(){
        setCounter(prev => prev + info.maxResults)
        console.log(info.firstResult)
    }

    return (

        <div className='container'>
            <div className='filter-find'>
                <div className='find'>
                    <input type={'text'} name='firstName' placeholder='teacher first name' onChange={handleChange} />
                </div>
                <div className='find'>
                    <input type={'text'} name='lastName' placeholder='teacher last name' onChange={handleChange} />
                </div>
                <div className='find'>
                    <input type={'text'} name='email' placeholder='Email' onChange={handleChange} />
                </div>
                <div className='find'>
                    <input type={'text'} name='phone' placeholder='teacher phone' onChange={handleChange} />
                </div>
                <div className='find'>
                    <input type={'text'} name='school' placeholder='School' onChange={handleChange} />
                </div>
                <div className='find'>
                    <select placeholder='Degree' name="degree" onChange={handleChange}>
                        <option value="" disabled selected>Degree</option>
                        <option value="teacher">teacher</option>
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
                <div className='find'>
                    <input type={'text'} name='ssn' placeholder='teacher SSN' onChange={handleChange} />
                </div>
                <div className='find'>
                    <input type="date" id="birth" name='birthDate' placeholder='Birthdate' max="2030-12-31" onChange={handleChange} />
                </div>
                <div className='find'>
                    <select name='sortBy' onChange={handleChange}>
                        <option value="firstName" selected>First Name</option>
                        <option value="lastName">Last Name</option>
                        <option value="degree">Degree</option>
                        <option value="school">School</option>
                        <option value="birthDate">Birthdate</option>
                        <option value="id">id</option>
                        <option value="ssn">ssn</option>
                        <option value="phone">phone</option>
                    </select>
                </div>
                <div className='find'>
                    <select name='tagmode' onChange={handleChange}>
                        <option value="and" selected>AND</option>
                        <option value="or">OR</option>
                    </select>
                </div>
                <div className='find'>
                    <select name='order' onChange={handleChange}>
                        <option value="asc" selected>ascending</option>
                        <option value="desc">descending</option>
                    </select>
                </div>
                <div className='find'>
                    <select name='hasPrivilege' onChange={handleChange}>
                        <option value="admin">admin</option>
                        <option value="isn't admin">Not admin</option>
                        <option value="both" selected>Both</option>
                    </select>
                </div>
                <button className='btn btn-dark button' onClick={handleClick}>Find/Sort</button>
            </div>

            <div className='container alldata'>
                <table className="table-container container">
                    <thead>
                        <tr>
                            <th><h1>teacher ID</h1></th>
                            <th><h1>First Name</h1></th>
                            <th><h1>Last Name</h1></th>
                            <th><h1>Email</h1></th>
                            <th><h1>Phone</h1></th>
                            <th><h1>School</h1></th>
                            <th><h1>Degree</h1></th>
                            <th><h1>SSN</h1></th>
                            <th><h1>Birthdate</h1></th>
                            <th><h1>Admin OR Not</h1></th>
                        </tr>
                    </thead>

                    <tbody>
                        {teacherData.map((teacher) => (
                            <tr key={teacher.id}>
                                <td>{teacher.id}</td>
                                <td>{teacher.firstName}</td>
                                <td>{teacher.lastName}</td>
                                <td>{teacher.email}</td>
                                <td>{teacher.phone}</td>
                                <td>{teacher.school}</td>
                                <td>{teacher.degree}</td>
                                <td>{teacher.ssn}</td>
                                <td>{teacher.birthDate}</td>
                                <td>{teacher.hasPrivilege.toString()}</td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>
            <div className='pages'>
                <button className='btn btn-dark page-back page'>Previous <GrCaretPrevious /></button>
                <button className='btn btn-dark page-next page' onClick={handleNext}>Next <GrCaretNext /></button>
            </div>
        </div>
    )
}
export default TeacherTable;
