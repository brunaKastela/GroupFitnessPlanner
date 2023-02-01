import React, { Component, useEffect } from "react";
import { useSelector }  from "react-redux";
import { useNavigate, Navigate } from "react-router-dom";
import '../static/style.css';
import TrainingTable from './TrainingTable.jsx';

function Table(props) {
    const isLoggedIn = useSelector((state) => state.user.logged);
    const navigate = useNavigate();
    const userType = localStorage.getItem("userType");
    
    useEffect(() => {
        if(!isLoggedIn) {
            navigate("/login");
        }

        
        if(userType === "COACH") {
            navigate("/userProfile");
        }
      });
    

    return (
        <div>
            <div>
                
            </div>
            <TrainingTable/>
        </div>
        
    );


  }

  export default Table;