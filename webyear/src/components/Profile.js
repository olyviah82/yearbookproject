import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { DataGrid } from '@mui/x-data-grid';
import { Avatar } from '@mui/material';



const deactivateUser = async (userId) => {
  const confirmDeactivate = window.confirm("Are you sure you want to deactivate?");
  if (confirmDeactivate) {
    try {
      const url = `http://localhost:8080/users?id=${userId}&active=false`;
      await axios.patch(url);
      console.log('User deactivated successfully.');
      window.location.reload(); // Refresh the page
    } catch (error) {
      console.error('Error deactivating user:', error);
    }
  }
};
const columns = [
  { field: 'id', headerName: 'ID', width: 70 },
  {
    field: 'image',
    headerName: 'image',
    width: 120,
    renderCell: (params) => (
      <Avatar
        alt="User Image"
        src={params.value}
        sx={{ width: 56, height: 56 }}
      />
    ),
  },
  { field: 'firstname', headerName: 'First name', width: 80 },
  { field: 'lastname', headerName: 'Last name', width: 80 },
  {
    field: 'dob', headerName: 'Date of Birth', width: 90
  },
  {
    field: 'fullName',
    headerName: 'Full name',
    description: 'This column has a value getter and is not sortable.',
    sortable: false,
    width: 160,
    valueGetter: (params) =>
      `${params.row.firstname || ''} ${params.row.lastname || ''}`,
  },
  { field: 'email', headerName: 'Email Address', width: 130 },
  { field: 'bio', headerName: 'Bio', width: 160 },
  { field: 'yearGraduation', headerName: 'Year of Graduation', width: 90 },
  { field: 'faculty', headerName: 'faculty', width: 90 },
  {
    field: 'actions',
    headerName: 'Action',
    width: 200,
    renderCell: (params) => (
      <div>
        <Link className="btn btn-primary mx-2" to={`/viewuser/${params.row.id}`}>
          View
        </Link>
        <Link className="btn btn-outline-primary mx-2" to={`/edituser/${params.row.id}`}>
          Edit
        </Link>
        <button className="btn btn-secondary tooltips"
                    data-placement="top"
                    data-toggle="tooltip"
                    data-original-title="Deactivate"
                    onClick={() => deactivateUser(params.row.id)}
                  >
                    <i className="fa fa-times"></i>
                  </button>
        </div>
    ),
  },
];








const Profile = () => {
  const [users, setUsers] = useState([]);
  const { id } = useParams();
  const navigate = useNavigate();
  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    try {
      const response = await axios.get("http://localhost:8080/users/all?page=0&size=10");
      console.log(response.data); // Verify the data structure
      setUsers(response.data.content);
    } catch (error) {
      console.log(error);
    }
  };
  const deactivateUser = async (userId) => {
    const confirmDeactivate = window.confirm("Are you sure you want to deactivate?");
    if (confirmDeactivate) {
      try {
        const url = `http://localhost:8080/users?id=${userId}&active=false`;
        await axios.patch(url);
        console.log('User deactivated successfully.');
        window.location.reload(); // Refresh the page
      } catch (error) {
        console.error('Error deactivating user:', error);
      }
    }
  };


  const rows = users.map((user, index) => ({
    id: user.id,
    image: `http://localhost:8080${user.imageUrl}`,
    firstname: user.firstName,
    lastname: user.lastName,
    dob: user.dob,
    email: user.email,
    bio: user.bio,
    yearGraduation: user.yearGraduation,
    faculty: user.faculty
  }));


  return (
    <div className="container">
      <div className="row">

        <div style={{ height: 400, width: '90%' }}>
          <DataGrid
          autoHeight
            rows={rows}
            columns={columns}
            initialState={{
              pagination: {
                paginationModel: { page: 0, pageSize: 5 },
              },
            }}
            pageSizeOptions={[5, 10]}
            checkboxSelection
          />
        </div>


        
      </div>
    </div>



  );

}

export default Profile;
