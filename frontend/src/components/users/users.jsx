// src/components/Dashboard/Dashboard.jsx
import React, { useState } from 'react';
import { Link } from "react-router-dom"

// import "./style.css";

const Users = ({ shopItems }) => {
  const [isOpen, setIsOpen] = useState(false);

  const togglePopup = () => {
    setIsOpen(!isOpen);
  };

  const data = [
    { id: 1, name: "Alice", age: 24, job: "Designer" },
    { id: 2, name: "Bob", age: 28, job: "Developer" },
    { id: 3, name: "Charlie", age: 22, job: "Analyst" },
    { id: 4, name: "Diana", age: 26, job: "Manager" },
  ];
  return (
    <div className='dashboard'>

      <div className='first'>
      <div className="sidebar">
      <h2>Dashboard</h2>
      <Link to="/product">Products</Link>
      <Link to="/countproduct">CountProduct</Link>
      <Link to="/users">Users</Link>
      <Link to="/logout" className="logout">Log Out</Link>
    </div>
      </div>
      <div className='second'>
        <div className="table-container">

          
          <h2>Users table</h2>
          <table className="custom-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Age</th>
                <th>Modify</th>
                <th>Delete</th>
              </tr>
            </thead>
            <tbody>
            {data.map((user) => (
            <tr key={user.id}>
              <td>#{user.id}</td>
              <td>{user.name}</td>
              <td>{user.age}</td>
              <td> <button onClick="" className="btn-dash edit"><i class="fas fa-edit"></i> </button></td>
                  <td> <button onClick="" className="btn-dash red"><i class="fa-solid fa-trash"> </i>  </button></td>

            </tr>
          ))}
            </tbody>
          </table>
        </div>

      </div>


    </div>
  );
};

export default Users;