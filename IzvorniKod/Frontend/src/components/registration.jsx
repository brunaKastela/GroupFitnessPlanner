import React from 'react';
import { Link, Navigate, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { toast } from "react-toastify";
import { CREATE_USER } from "../redux/actionTypes";
import axios from 'axios';
import { useEffect, useState } from 'react';
import { authHeader } from "../helpers/authHeader";
import '../static/forms.css';
import '../static/style.css';


export default function Registration(props) {
  const navigate = useNavigate();
  const [status, setStatus] = React.useState("CLIENT")
  const dispatch = useDispatch();
  const [regForm, setLoginForm] = React.useState({ name: '', surname: '', dateOfBirth: '', email: '', username: '', password: '', contact: '', goal: '' });
  const [error, setError] = React.useState('');
  const [goals, setGoals] = useState([]);

  useEffect(() => {
    goalsdata();
  }, []);

  const radioHandler = (status) => {
    setStatus(status);
  };

  const goalsdata = async () => {
    const { data } = await axios.get('https://group-fitness-planer-wf93.onrender.com/registration/goals');
    setGoals(data);
  };

  function onChange(event) {
    const { name, value } = event.target;
    console.log(value);
    setLoginForm(oldForm => ({ ...oldForm, [name]: value }))
  }

  const data = {
    name: regForm.name,
    surname: regForm.surname,
    dateOfBirth: regForm.dateOfBirth,
    username: regForm.username,
    password: regForm.password,
    contact: regForm.contact,
    email: regForm.email,
    goal: regForm.goal,
    role: status 
  };
  const data2 = {
    name: regForm.name,
    surname: regForm.surname,
    dateOfBirth: regForm.dateOfBirth,
    username: regForm.username,
    password: regForm.password,
    contact: regForm.contact,
    email: regForm.email,
    role: status 
  };


  //firstname




  function onSubmit(e) {
    e.preventDefault();
    setError("");
    console.log()
    console.log("HALO",data.dateOfBirth.trim().split(".")[2].charAt(3).localeCompare("9"))
    if (data.username.trim().length < 5 || data.username.trim().length > 20) {
      alert("Username must have 5-20 characters");
  } else if (data.password.trim().length < 8 || data.password.trim().length > 20) {
      alert("Password must have 8-20 characters");
  }else if (!data.dateOfBirth.includes(".") || data.dateOfBirth.trim().split(".").length != 3 ||
  data.dateOfBirth.trim().split(".")[0].length != 2 || data.dateOfBirth.trim().split(".")[0].charAt(0).localeCompare("0") == -1 ||
  data.dateOfBirth.trim().split(".")[0].charAt(0).localeCompare("9") == 1|| data.dateOfBirth.trim().split(".")[0].charAt(1).localeCompare("0") == -1 ||
  data.dateOfBirth.trim().split(".")[0].charAt(1).localeCompare("9") == 1|| data.dateOfBirth.trim().split(".")[1].charAt(1).localeCompare("0") == -1 ||
  data.dateOfBirth.trim().split(".")[1].charAt(1).localeCompare("9") == 1 ||
  data.dateOfBirth.trim().split(".")[1].length != 2 || data.dateOfBirth.trim().split(".")[1].charAt(0).localeCompare("0") == -1 ||
  data.dateOfBirth.trim().split(".")[0].charAt(0).localeCompare("9") == 1||
  data.dateOfBirth.trim().split(".")[2].length != 4 || data.dateOfBirth.trim().split(".")[2].charAt(0).localeCompare("0") == -1 ||
  data.dateOfBirth.trim().split(".")[2].charAt(0).localeCompare("9") == 1|| data.dateOfBirth.trim().split(".")[2].charAt(1).localeCompare("0") == -1 ||
  data.dateOfBirth.trim().split(".")[2].charAt(1).localeCompare("9") == 1|| data.dateOfBirth.trim().split(".")[2].charAt(2).localeCompare("0") == -1 ||
  data.dateOfBirth.trim().split(".")[2].charAt(2).localeCompare("9") == 1|| data.dateOfBirth.trim().split(".")[2].charAt(3).localeCompare("0") == -1 ||
  data.dateOfBirth.trim().split(".")[2].charAt(3).localeCompare("9") == 1) {
    alert("DOB must be in format dd.mm.yyyy");
}
  else if (data.contact.trim().length < 8 || data.contact.trim().length > 10) {
    alert("contact must have 8-20 characters");
} else{

     // const body = `name=${regForm.name}&surname=${regForm.surname}&dateOfBirth=${regForm.dateOfBirth}&email=${regForm.email}&username=${regForm.username}&password=${regForm.password}&contact=${regForm.contact}`;
    // const fromData = body;
    const options = {
      method: 'POST',
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
      },
      body: JSON.stringify(data)
    };
    const options2 = {
      method: 'POST',
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
      },
      body: JSON.stringify(data2)
    };

    

    if (status === "COACH") {
      fetch('https://group-fitness-planer-wf93.onrender.com/registration/coach', options2).then(response => {

        if (response.status != 200) {
          response.json().then(json => {
            alert(JSON.stringify(json['message']));
          });
        } else {
          response.json().then((json) => {
            console.log(json);
                        localStorage.setItem(
                            "user",
                            JSON.stringify({
                                userId: data2,
                                password:data2.password,
                                ...{
                                    authdata: window.btoa(
                                        `${data2.username}:${data2.password}`
                                    ),
                                },
                            })
                        );
            navigate('/userProfile');
            })            
          }
        });
    }
    else {
      console.log("JAAAAA");
      fetch('https://group-fitness-planer-wf93.onrender.com/registration/client', options).then(response => {

        if (response.status != 200) {
          console.log(response.json().then(json => {
            alert(JSON.stringify(json['message']));
          }));
        } else {
          response.json().then((jos) => {
            console.log(jos);
                        localStorage.setItem(
                            "user",
                            JSON.stringify({
                                userId: data,
                                password:data.password,
                                ...{
                                    authdata: window.btoa(
                                        `${data.username}:${data.password}`
                                    ),
                                },
                            })
                        );
            navigate('/userProfile');
            })            
          }
        }); }
  }


   

  }



  return (
    <div className="">
      <div className="form">
        <form onSubmit={onSubmit}>
          <div className="input-container">
            <label>Ime</label>
            <input name='name' onChange={onChange} value={regForm.name} />
          </div>
          <div className="input-container">
            <label>Prezime</label>
            <input name='surname' onChange={onChange} value={regForm.surname} />
          </div>
          <div className="input-container">
            <label>Datum rođenja</label>
            <input name='dateOfBirth' onChange={onChange} value={regForm.dateOfBirth} />
          </div>
          <div className="input-container">
            <label>E-mail</label>
            <input name='email' onChange={onChange} value={regForm.email} />
          </div>
          <div className="input-container">
            <label>Korisničko ime</label>
            <input name='username' onChange={onChange} value={regForm.username} />
          </div>
          <div className="input-container">
            <label>Lozinka</label>
            <input name='password' type="password" onChange={onChange} value={regForm.password} />
          </div>
          <div className="input-container">
            <label>Kontakt</label>
            <input name='contact' onChange={onChange} value={regForm.contact} />
          </div>
          <div className="input-container">
            <label>Odaberite ulogu: </label>
            <div style={{display:'block'}}>
            <input type="radio" name="gumb" id="gumb1" onChange={onChange} onClick={(e) => radioHandler("CLIENT")} value={regForm.role} defaultChecked />
            <label htmlFor="age1">vježbač</label><br />
            <input type="radio" name="gumb" id="gumb2" onChange={onChange} onClick={(e) => radioHandler("COACH")} value={regForm.role} />
            <label htmlFor="age2">trener</label><br />
            </div>
          </div>
          <div className='error'>{error}</div>
          {status === "CLIENT" && (<div className='goals'>
            <select name="goal" id="goalsselect" onChange={onChange}>
              <option value=""></option>
              {goals.map(goals => (
                <option key={goals} value={goals}>{goals}</option>
              ))}
            </select>
          </div>)}
          <div className="text-center">
                        <label >Već imate račun? <Link to="/login">Prijavite se</Link></label>
                    </div>
          <div className="button-holder">
            <input type="submit" onClick={onSubmit} value="REGISTRACIJA" />
          </div>
        </form>
      </div>
    </div>
  )
}