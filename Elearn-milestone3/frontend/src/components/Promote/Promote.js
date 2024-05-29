import axios from 'axios'
import React, { useState } from 'react'
import './Promote.css'
import {SERVER_URL} from '../../constants'
const Promote = () => {
    const [promotion,setPromotion] = useState({
        email : '',
        promotion : ''
    })
    function handleChange(event){
        setPromotion({...promotion,[event.target.name] : event.target.value})
    }
    const email = 'admin@admin.com'
    const password = 'admin'
    function handleClick(){
        console.log(`/admin/${promotion.promotion}/${promotion.email}`)
            axios.post(SERVER_URL + `/admin/${promotion.promotion}/${promotion.email}`, {}, {
            auth: {
                username: email,
                password: password
            }
            })
            .then(response => {
                alert(response.data)
                window.location.reload();
            })
            .catch(error => {
                // Handle errors here
                alert('Error:', error);
            });
    }
    return (
        <div className='container promote-container'>
            <h1>PROMOTION</h1>
            <div className='find promot-fields'>
                <input type={'text'} placeholder='Email' name={'email'} onChange={handleChange} />
            </div>
            <div className='find'>
                <select onChange={handleChange} name='promotion'>
                    <option disabled selected >Promotion</option>
                    <option value="promote">Promote</option>
                    <option value="demote">Demote</option>
                    <option value="delete">Delete</option>
                </select>
            </div>
            <button className='btn btn-primary button my-2' onClick={handleClick}>Submit Promotion</button>
        </div>
        
    )
}

export default Promote;
