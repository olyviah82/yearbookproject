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
    editable:true,
    renderCell: (params) => (
      <Avatar
        alt="User Image"
        src={params.value}
        sx={{ width: 56, height: 56 }}
      />
    ),
  },
  { field: 'firstname', headerName: 'First name', width: 80, editable: true,  headerClassName: 'super-app-theme--header',
  headerAlign: 'center', },
  { field: 'lastname', headerName: 'Last name', width: 80, editable: true,  headerClassName: 'super-app-theme--header',
  headerAlign: 'center', },
  {
    field: 'dob', headerName: 'Date of Birth',type: 'date', width: 90, editable: true, valueGetter: (params) => new Date(params.value),  headerClassName: 'super-app-theme--header',
    headerAlign: 'center',
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
  { field: 'email', headerName: 'Email Address', width: 130, editable: true },
  { field: 'bio', headerName: 'Bio', width: 160, editable: true },
  { field: 'yearGraduation', headerName: 'Year of Graduation', width: 90, editable: true },
  { field: 'faculty', headerName: 'faculty', width: 90, editable: true },
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
  const handleEditCellChangeCommitted = async (params) => {
    const field = params.field;
    const value = params.value;
    const id = params.id;
  
    try {
      await axios.put(`http://localhost:8080/user/${id}`, { [field]: value });
      console.log('User updated successfully.');
    } catch (error) {
      console.error('Error updating user:', error);
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

  const [selectedFiles, setSelectedFiles] = useState([]);

  const handleFileChange = (event) => {
    const files = event.target.files;
    setSelectedFiles(files);
  };
  return (
    <div className="container-fluid">
      <div className="row">

        <div style={{ height: 400, width: '100%', alignContent:'center',justifyContent:'center'  }}>
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
            disableSelectionOnClick
            onEditCellChangeCommitted={handleEditCellChangeCommitted}
          />
        </div>



      </div>
    </div>



  );

}

export default Profile;
