import axios from 'axios';
import React, { useState } from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import DatePicker from 'react-datepicker';
import 'react-datepicker/dist/react-datepicker.css';
import { useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';

const schema = yup.object().shape({
  firstName: yup.string().required('First name is required').min(3, 'First name must be at least 3 characters'),
  lastName: yup.string().required('Last name is required'),
  email: yup.string().email('Invalid email format').required('Email is required'),
  bio: yup.string().required('Bio is required').min(10, 'Bio must be at least 10 characters'),
  yearGraduation: yup.number().required('Year graduation is required').min(1970, 'Year graduation must be greater than or equal to 1970'),
  faculty: yup.string().required('Faculty is required'),
});

const AddUser = () => {
  const { register, handleSubmit, formState: { errors } } = useForm({
    resolver: yupResolver(schema),
  });

  const navigate = useNavigate();

  const [user, setUser] = useState({
    firstName: '',
    lastName: '',
    dob: null,
    email: '',
    bio: '',
    yearGraduation: '',
    faculty: '',
  });

  const { firstName, lastName, dob, email, bio, yearGraduation, faculty } = user;

  const onDateChange = (date) => {
    setUser({ ...user, dob: date });
  };

  const formatDate = (date) => {
    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = String(date.getFullYear());
    return `${day}-${month}-${year}`;
  };

  const onInputChange = (e) => {
    setUser({ ...user, [e.target.name]: e.target.value });
  };

   const onSubmit = async (e) => {

    const formattedDob = formatDate(dob);
    await axios.post(`http://localhost:8080/users?firstName=${firstName}&lastName=${lastName}&dob=${formattedDob}&email=${email}&bio=${bio}&yearGraduation=${yearGraduation}&faculty=${faculty}`);
    navigate('/profile');
  };

  return (
    <div className='container-fluid'>
      <div className='row justify-content-center'>
        <div className='col-12 col-sm-10 col-md-12 col-lg-11 col-xl-10'>
          <div className='card d-flex mx-auto my-5'>
            <div className='row'>
              <div className="col-md-5 col-sm-12 col-xs-12 c1 p-5">
                <img src="https://st2.depositphotos.com/1637056/6516/v/600/depositphotos_65162857-stock-illustration-diverse-happy-people.jpg" width="120vw" height="210vh" class="mx-auto d-flex" alt="Teacher" />
                <div className="row justify-content-center">
                  <div className="w-75 mx-md-5 mx-1 mx-sm-2 mb-5 mt-4 px-sm-5 px-md-2 px-xl-1 px-2">
                    <h1 className="wlcm">Welcome to your blackboard</h1>
                    <span className="sp1">
                      <span className="px-3 bg-danger rounded-pill"></span>
                      <span className="ml-2 px-1 rounded-circle"></span>
                      <span className="ml-2 px-1 rounded-circle"></span> </span>
                  </div>
                </div>
              </div>
              <div className="col-md-7 col-sm-12 col-xs-12 c2 px-5 pt-5">
                <form onSubmit={handleSubmit(onSubmit)} class="px-5 pb-5">
                  <Container>
                    <Row>
                      
                        <div className="form-group">
                          <label>First Name</label>
                          <input
                            type="text"
                            className={`form-control ${errors.firstName ? 'is-invalid' : ''}`}
                            name="firstName"
                            value={user.firstName}

                            {...register('firstName', { required: true, minLength: 3 })}
                            onChange={onInputChange}
                          />
                          {errors.firstName && (
                            <small className="text-danger">{errors.firstName.message}</small>
                          )}
                        </div>
                      
                      
                        <div className="form-group">
                          <label>Last Name</label>
                          <input
                            type="text"
                            className={`form-control ${errors.lastName ? 'is-invalid' : ''}`}
                            name="lastName"
                            value={lastName}
                            {...register('lastName', { required: true, minLength: 3 })}
                            onChange={onInputChange}
                          />
                          {errors.lastName && (
                            <small className="text-danger">{errors.lastName.message}</small>
                          )}
                        </div>
                      
                    </Row>
                    <Row>
                      
                        <div className="form-group">
                          <label>Date of Birth</label>
                          <DatePicker
                            id="dob"
                            name="dob"
                            selected={dob}
                            dateFormat="dd-MM-yyyy"
                            className='form-control'
                            onChange={onDateChange}
                          
                          />

                        </div>
                      
                      
                        <div className="form-group">
                          <label>Email</label>
                          <input
                            type="email"
                            className={`form-control ${errors.email ? 'is-invalid' : ''}`}
                            name="email"
                            value={email}
                            {...register('email', { required: true })}
                            onChange={onInputChange}

                          />
                          {errors.email && (
                            <small className="text-danger">{errors.email.message}</small>
                          )}
                        </div>
                      
                    </Row>
                    <Row>
                      
                        <div className="form-group">
                          <label>Bio</label>
                          <input
                            type="text"
                            className={`form-control ${errors.bio ? 'is-invalid' : ''}`}
                            name="bio"
                            value={bio}
                            {...register('bio', { required: true, minLength: 10 })}
                            onChange={onInputChange}

                          />
                          {errors.bio && (
                            <small className="text-danger">{errors.bio.message}</small>
                          )}
                        </div>
                      
                      
                        <div className="form-group">
                          <label>Year of Graduation</label>
                          <input
                            type="text"
                            className={`form-control ${errors.yearGraduation ? 'is-invalid' : ''
                              }`}
                            name="yearGraduation"
                            value={yearGraduation}

                            {...register('yearGraduation', { required: true, min: 1970 })}
                            onChange={onInputChange}
                          />
                          {errors.yearGraduation && (
                            <small className="text-danger">
                              {errors.yearGraduation.message}
                            </small>
                          )}
                        </div>
                      
                      
                        <div className="form-group">
                          <label>Faculty</label>
                          <input
                            type="text"
                            className={`form-control ${errors.faculty ? 'is-invalid' : ''}`}
                            name="faculty"
                            value={faculty}

                            {...register('faculty', { required: true })}
                            onChange={onInputChange}
                          />
                          {errors.faculty && (
                            <small className="text-danger">{errors.faculty.message}</small>
                          )}
                        </div>
                      
                    </Row>
                    <br />
                    <button type="submit" className="btn btn-block btn-danger">
                      Create User
                    </button>
                  </Container>
                </form>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    

  );
};

export default AddUser;
