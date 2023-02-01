import React from "react";

import { Navigate, useNavigate } from "react-router";
import "../static/home.css"

function Home(props) {
    const navigate = useNavigate()
    function goToLogin (){  
        navigate('/login');
      }
    return (
        <div className="button-container">
            <div className = "motiv">
                Neorganizirani ste?
                Više nema izlika!
            </div>
         <button className="button" onClick = {goToLogin}>
            GET IN SHAPE
         </button>   
        </div>
    );
}

export default Home;