// src/components/Sidebar/Sidebar.jsx
import React from 'react';
import { Link } from 'react-router-dom';

const Sidebar = ({ sidebarColor, sidebarType, toggleSidebar }) => {
  return (
    <aside 
      className={`sidenav navbar navbar-vertical navbar-expand-xs border-0 border-radius-xl my-3 fixed-start ms-3 ${sidebarType} ${sidebarColor}`} 
      id="sidenav-main"
    >
      <div className="sidenav-header">
        <i 
          className="fas fa-times p-3 cursor-pointer text-secondary opacity-5 position-absolute end-0 top-0 d-none d-xl-none" 
          aria-hidden="true" 
          id="iconSidenav"
          onClick={toggleSidebar}
        ></i>
        <Link className="navbar-brand m-0" to="/">
          <img src="/assets/img/logo-ct-dark.png" className="navbar-brand-img h-100" alt="main_logo" />
          <span className="ms-1 font-weight-bold">Soft UI Dashboard 3</span>
        </Link>
      </div>
      <hr className="horizontal dark mt-0" />
      <div className="collapse navbar-collapse w-auto" id="sidenav-collapse-main">
        <ul className="navbar-nav">
          {/* Dashboard Link */}
          <li className="nav-item">
            <Link className="nav-link active" to="/">
              <div className="icon icon-shape icon-sm shadow border-radius-md bg-white text-center me-2 d-flex align-items-center justify-content-center">
                <svg width="12px" height="12px" viewBox="0 0 45 40" version="1.1" xmlns="http://www.w3.org/2000/svg">
                  {/* SVG path for dashboard icon */}
                </svg>
              </div>
              <span className="nav-link-text ms-1">Dashboard</span>
            </Link>
          </li>
          
          {/* Tables Link */}
          <li className="nav-item">
            <Link className="nav-link" to="/tables">
              <div className="icon icon-shape icon-sm shadow border-radius-md bg-white text-center me-2 d-flex align-items-center justify-content-center">
                <svg width="12px" height="12px" viewBox="0 0 42 42" version="1.1" xmlns="http://www.w3.org/2000/svg">
                  {/* SVG path for tables icon */}
                </svg>
              </div>
              <span className="nav-link-text ms-1">Tables</span>
            </Link>
          </li>
          
          {/* Add other menu items similarly */}
          
          {/* Account Pages Section */}
          <li className="nav-item mt-3">
            <h6 className="ps-4 ms-2 text-uppercase text-xs font-weight-bolder opacity-6">Account pages</h6>
          </li>
          
          {/* Profile Link */}
          <li className="nav-item">
            <Link className="nav-link" to="/profile">
              <div className="icon icon-shape icon-sm shadow border-radius-md bg-white text-center me-2 d-flex align-items-center justify-content-center">
                <svg width="12px" height="12px" viewBox="0 0 46 42" version="1.1" xmlns="http://www.w3.org/2000/svg">
                  {/* SVG path for profile icon */}
                </svg>
              </div>
              <span className="nav-link-text ms-1">Profile</span>
            </Link>
          </li>
          
          {/* Sign In Link */}
          <li className="nav-item">
            <Link className="nav-link" to="/sign-in">
              <div className="icon icon-shape icon-sm shadow border-radius-md bg-white text-center me-2 d-flex align-items-center justify-content-center">
                <svg width="12px" height="12px" viewBox="0 0 40 44" version="1.1" xmlns="http://www.w3.org/2000/svg">
                  {/* SVG path for sign in icon */}
                </svg>
              </div>
              <span className="nav-link-text ms-1">Sign In</span>
            </Link>
          </li>
          
          {/* Sign Up Link */}
          <li className="nav-item">
            <Link className="nav-link" to="/sign-up">
              <div className="icon icon-shape icon-sm shadow border-radius-md bg-white text-center me-2 d-flex align-items-center justify-content-center">
                <svg width="12px" height="20px" viewBox="0 0 40 40" version="1.1" xmlns="http://www.w3.org/2000/svg">
                  {/* SVG path for sign up icon */}
                </svg>
              </div>
              <span className="nav-link-text ms-1">Sign Up</span>
            </Link>
          </li>
        </ul>
      </div>
      <div className="sidenav-footer mx-3">
        <div className="card card-background shadow-none card-background-mask-secondary" id="sidenavCard">
          <div className="full-background" style={{ backgroundImage: "url('/assets/img/curved-images/white-curved.jpg')" }}></div>
          <div className="card-body text-start p-3 w-100">
            <div className="icon icon-shape icon-sm bg-white shadow text-center mb-3 d-flex align-items-center justify-content-center border-radius-md">
              <i className="ni ni-diamond text-dark text-gradient text-lg top-0" aria-hidden="true" id="sidenavCardIcon"></i>
            </div>
            <div className="docs-info">
              <h6 className="text-white up mb-0">Need help?</h6>
              <p className="text-xs font-weight-bold">Please check our docs</p>
              <a href="https://www.creative-tim.com/learning-lab/bootstrap/license/soft-ui-dashboard" target="_blank" rel="noreferrer" className="btn btn-white btn-sm w-100 mb-0">Documentation</a>
            </div>
          </div>
        </div>
        <a className="btn btn-primary mt-3 w-100" href="https://www.creative-tim.com/product/soft-ui-dashboard-pro?ref=sidebarfree">Upgrade to pro</a>
      </div>
    </aside>
  );
};

export default Sidebar;