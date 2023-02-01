import React from 'react';
import {useRef} from 'react';
import { Link, Navigate, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { CREATE_USER } from "../redux/actionTypes";
import axios from 'axios';
import { useEffect, useState } from 'react';
import { authHeader } from "../helpers/authHeader";
import '../static/forms.css';
import '../static/style.css';
import TodoList from './ToDoList'
import Todo from './ToDo'
import Tabe from './Tabe'
import { LOGIN } from "../redux/actionTypes";



export default function CreateTraining(props) {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const [training, setTraining] = React.useState({ name: '', surname: '', dateOfBirth: '', email: '', username: '', password: '11111111', contact: '', goal: '' });
  const [error, setError] = React.useState('');
  const userID = localStorage.getItem("userID");
  let username = ""
  let password = ""
  username = JSON.parse(localStorage.getItem("user")).userId.username;
  password = JSON.parse(localStorage.getItem("user")).password;

  const init = async () => {
    await availableWorkouts();
  }

  useEffect(() => {
     init();
  }, []);



  const availableWorkouts = async () => {
    const req_body = {
        username: username,
        password:password
        }

        console.log(req_body);
        const options = {

            method: "POST",
            headers: {
              "Content-Type": "application/json; charset=UTF-8",
            },  
            body: JSON.stringify(req_body)
        };

        
        const response = await fetch('https://group-fitness-planer-wf93.onrender.com/login/user', options);

        if (response.status != 200) {
          response.json().then(json => {
            alert(JSON.stringify(json['message']));
          });
          } else {
            const json = await response.json();
                console.log('json', json)
                setTraining(json);
                
                
          }
    
  }


  const onChange = (event) => {
    const { name, value } = event.target;
    setTraining(oldForm => {
      return {...oldForm,[name]:value}
    })
    console.log("AAAAAAAAAAAAAAAA",training);
  }
  const data = {
    name: training.name,
    surname: training.surname,
    dateOfBirth: training.dateOfBirth,
    username: training.username,
    password: password,
    newPassword: training.password,
    contact: training.contact,
    userID:userID
 //   email: regForm.email,
  //  goal: regForm.goal
  };
 

  function onSubmit(e) {

    console.log("OVDJEEEE SMO",data);
    e.preventDefault();
    setError("");

    const options = {
      method: 'POST',
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
      },
      body: JSON.stringify(data)
    };

  fetch('https://group-fitness-planer-wf93.onrender.com/user/updateinfo', options).then(response => {

    if (response.status != 200) {
      console.log(response.json().then(json => {
        alert(JSON.stringify(json['message']));
      }));
    } else {
      response.json().then((json) => {
        alert("OK");
        console.log(json);
                    localStorage.setItem(
                        "user",
                        JSON.stringify({
                            userId: data,
                            password:password,
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
    });

  }

  function navigator() {
    navigate('/userprofile')
  }
//{console.log('to je ovaj', JSON.stringify(todos))}
  return (
    <div className="">
      <div className="form">
        <form onSubmit={onSubmit}>
          <div className="input-container">
            <label>Ime</label>
            <input name='name' onChange={onChange} value={training.name} />
          </div>
          <div className="input-container">
            <label>Prezime</label>
            <input name='surname' onChange={onChange} value={training.surname} />
          </div>
          <div className="input-container">
            <label>Datum rođenja</label>
            <input name='dateOfBirth' onChange={onChange} value={training.dateOfBirth} />
          </div>
          <div className="input-container">
            <label>Korisničko ime</label>
            <input name='username' onChange={onChange} value={training.username} />
          </div>
          <div className="input-container">
            <label>Lozinka</label>
            <input name='password' type="password" onChange={onChange} value={training.password} />
          </div>
          <div className="input-container">
            <label>Kontakt</label>
            <input name='contact' onChange={onChange} value={training.contact} />
          </div>
          <div className='error'>{error}</div>
          <div className="button-row">
            <div className="button-holder">
              <input class="green-button" type="submit" onClick={() => navigator()} value="ODUSTANI" />
            </div>
            <div className="button-holder">
              <input class="green-button" type="submit" onClick={(onSubmit)} value="SPREMI PROMJENE" />
            </div>
          </div>
        </form>
      </div>
    </div>
  )
}
