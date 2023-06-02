import React, { useEffect, useState } from 'react'
import { Link, useNavigate, useParams } from 'react-router-dom'
import axios from 'axios';

export default function EditUser() {
    const navigate = useNavigate();
    const { id } = useParams();

    const [user, setUser] = useState({
        firstName: '',
        lastName: '',
        dob: '',
        email: '',
        bio: '',
        yearGraduation: '',
        faculty: ''
    })

    const { firstName, lastName, dob, email, bio, yearGraduation, faculty } = user;

    const onInputChange = (e) => {
        setUser({ ...user, [e.target.name]: e.target.value });
    };

    useEffect(() => {
        loadUser();
    }, []);

    const onSubmit = async (e) => {
        e.preventDefault();
        await axios.put(`http://localhost:8080/users?id=${id}`, {
            firstName,
            lastName,
            dob,
            email,
            bio,
            yearGraduation,
            faculty
        });
        navigate('/profile');
    };

    const loadUser = async () => {
        const result = await axios.get(`http://localhost:8080/users?id=${id}`);
        setUser(result.data)
    }

    return (
        <div className='container'>
            <div className='row'>
                <div className='col-md-6 offset-md-3 border rounded pt-4 mt-2 shadow'>
                    <h2 className='text-center m-4'> Edit User </h2>
                    <form onSubmit={(e) => onSubmit(e)}>

                        <div className='mb-3'>
                            <label htmlFor='firstName' className='form-label'>
                                First Name
                            </label>
                            <input
                                type='text'
                                className='form-control'
                                placeholder='Enter your first name'
                                name='firstName'
                                value={firstName}
                                onChange={(e) => onInputChange(e)} />
                        </div>
                        <div className='mb-3'>
                            <label htmlFor='lastName' className='form-label'>
                                Last Name
                            </label>
                            <input
                                type='text'
                                className='form-control'
                                placeholder='Enter your last name'
                                name='lastName'
                                value={lastName}
                                onChange={(e) => onInputChange(e)} />
                        </div>
                        <div className='mb-3'>
                            <label htmlFor='dob' className='form-label'>
                                Date of Birth
                            </label>
                            <input
                                type='text'
                                className='form-control'
                                placeholder='Enter your date of birth'
                                name='dob'
                                value={dob}
                                onChange={(e) => onInputChange(e)} />
                        </div>
                        <div className='mb-3'>
                            <label htmlFor='email' className='form-label'>
                                Email
                            </label>
                            <input
                                type='email'
                                className='form-control'
                                placeholder='Enter your email'
                                name='email'
                                value={email}
                                onChange={(e) => onInputChange(e)} 
                                disabled/>
                        </div>
                        <div className='mb-3'>
                            <label htmlFor='bio' className='form-label'>
                                Bio
                            </label>
                            <input
                                type='text'
                                className='form-control'
                                placeholder='Enter your bio'
                                name='bio'
                                value={bio}
                                onChange={(e) => onInputChange(e)} />
                        </div>
                        <div className='mb-3'>
                            <label htmlFor='yearGraduation' className='form-label'>
                                Year Graduation
                            </label>
                            <input
                                type='number'
                                className='form-control'
                                placeholder='Enter your year of graduation'
                                name='yearGraduation'
                                value={yearGraduation}
                                onChange={(e) => onInputChange(e)} />
                        </div>
                        <div className='mb-3'>
                            <label htmlFor='faculty' className='form-label'>
                                Faculty
                            </label>
                            <input
                                type='text'
                                className='form-control'
                                placeholder='Enter your faculty'
                                name='faculty'
                                value={faculty}
                                onChange={(e) => onInputChange(e)} />
                        </div>
                        <button type='submit' className='btn btn-outline-primary'>Submit</button>
                        <Link className='btn btn-outline-danger mx-2' to="/">Cancel</Link>
                    </form>
                </div>
            </div>
        </div>
    )
}
