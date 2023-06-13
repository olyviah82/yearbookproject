import axios from 'axios';
import React, { useState, useEffect } from 'react';
import { Col, Container, Row } from 'react-bootstrap';
import { Link, useNavigate, useParams } from 'react-router-dom';

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



  return (
    <div className="container">
      <div className="row">
        {users.map((user) => (
          <div key={user.id} className="col-md-6 col-xl-3">
            <div className="card m-b-30">
              <div className="card-body row">
                <div className="col-6">
                  <a href=""><img src={`http://localhost:8080${user.imageUrl}`}  alt="" className="img-fluid rounded-circle " /></a>
                </div>
                <div className="col-6 card-title align-self-center mb-0 single_advisor_details_info">
                  <h5>{user.firstName} {user.lastName}</h5>
                  <p className="m-0">{user.bio}</p>
                </div>
              </div>
              <ul className="list-group list-group-flush">
                <li className="list-group-item">
                  <i className="fa fa-envelope float-right"></i>Email : <a href={`mailto:${user.email}`}>{user.email}</a>
                </li>
                <li className="list-group-item">
                  <i className="fa fa-phone float-right"></i>Phone : {user.faculty}
                </li>
              </ul>
              <div className="card-body">
                <div className="float-right btn-group btn-group-sm">
                  <Link className="btn btn-primary tooltips" 
                  data-placement="top" data-toggle="tooltip"
                   data-original-title="Edit" to={`/edituser/${user.id}`}>
                    <i className="fa fa-pencil"></i>
                  </Link>
                  <Link className="btn btn-primary tooltips" 
                  data-placement="top" data-toggle="tooltip"
                   data-original-title="View" to={ `/viewuser/${user.id}`}><i className="fa fa-eye"></i></Link>
                  <button className="btn btn-secondary tooltips"
                    data-placement="top"
                    data-toggle="tooltip"
                    data-original-title="Deactivate"
                    onClick={() => deactivateUser(user.id)}
                  >
                    <i className="fa fa-times"></i>
                  </button>
                </div>

              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Profile;
