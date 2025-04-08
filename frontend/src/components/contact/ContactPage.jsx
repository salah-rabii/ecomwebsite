// src/pages/ContactPage.jsx
import React from "react";
import "./ContactForm.css"; // Import your CSS file for styling
import ContactForm from './ContactForm';
// import Image from '../assets/images/contacti.jpg'

const ContactPage = () => {
  return (
    <div>
      <h1 style={{ textAlign: "center", marginTop: "50px" }}>
        Get in Touch with Us
      </h1>
      <div className="contact-container">
        <img
          src="https://images.pexels.com/photos/2351858/pexels-photo-2351858.jpeg"// Replace with the path to your image
          alt="../assets/images/contacti.jpg"
          className="contact-image"
        />
        <ContactForm />
      </div>
    </div>
  );
};

export default ContactPage;
