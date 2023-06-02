import React from 'react'
import { Link } from 'react-router-dom'

export default function Navigation() {
  return (
    <div>
      <nav className="navbar navbar-expand-lg">
        <div className="container-fluid">
          <a className="navbar-brand" href="#">Year Project Application</a>
          <Link className='btn btn-outline-dark' to="/profile">Profile </Link>
          <button className="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            
            <span className="navbar-toggler-icon"></span>
          </button>
          <Link className='btn btn-outline-dark' to="/add">Add User</Link>
        </div>
      </nav>
    </div>
  )
}