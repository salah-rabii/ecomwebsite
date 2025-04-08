import React from "react";

const Catg = ({ setCategory }) => {  // Ensure setCategory is received as a prop
  const data = [
    { cateImg: "./images/category/cat-3.png", cateName: "Kids" },
    { cateImg: "./images/category/cat-2.png", cateName: "Men" },
    { cateImg: "./images/category/cat-1.png", cateName: "Women" },
  ];

  return (
    <div className="category">
      <div className="chea d_flex">
        <h1>FILTER</h1>
      </div>
      {data.map((value, index) => (
        <div
          className="box f_flex"
          key={index}
          onClick={() => setCategory(value.cateName.toLowerCase())} // Ensure it's lowercase to match your products
          style={{ cursor: "pointer" }}
        >
          <img src={value.cateImg} alt={value.cateName} />
          <span>{value.cateName}</span>
        </div>
      ))}
    </div>
  );
};

export default Catg;
