import React, { Component } from "react";
import { useSelector } from "react-redux";
import { Navigate } from "react-router-dom";
import { useNavigate } from "react-router";
import Moment from 'react-moment';
import '../static/style.css';


function UserProfile(props) {
  const navigate = useNavigate()
  const moment = require('moment');
  function goToEdit() {
    navigate('/editProfile')
  }
  function canChangeGoal() {
    var now = moment();
    var nextWeek = moment();
    nextWeek.add(7, 'days');

    if (now.month() === nextWeek.month()) {
      return false;
    } else {
      return true;
    }
  }

  // uzimamo first name iz local storagea
  console.log(localStorage)
  const isLoggedIn = useSelector((state) => state.user.logged);
  let firstName = ""
  let username = ""
  let surname = ""
  let dateOfBirth = ""
  let email = ""
  let contact = ""
  let role = ""
  let currentGoal = ""
  if (isLoggedIn) {
    firstName = JSON.parse(localStorage.getItem("user")).userId.name;
    username = JSON.parse(localStorage.getItem("user")).userId.username;
    surname = JSON.parse(localStorage.getItem("user")).userId.surname;
    dateOfBirth = JSON.parse(localStorage.getItem("user")).userId.dateOfBirth;
    email = JSON.parse(localStorage.getItem("user")).userId.email;
    contact = JSON.parse(localStorage.getItem("user")).userId.contact;
    role = JSON.parse(localStorage.getItem("user")).userId.role;
    if (role === "CLIENT") {
      currentGoal = JSON.parse(localStorage.getItem("user")).userId.currentGoal.goalName;
    }
    // dohvati jel trener
  }


  if (isLoggedIn) {
    // ako je trener
    // prilagodi stranicu treneru

    // inače
    // prilagodi stranicu vjezbacu
    if (role === "COACH" || role === "ADMIN") {

      return (
        <><div className="label1">
          Hello, {firstName}!
        </div>
          <div className="label2">Ime: {firstName}</div>
          <div className="label2">Prezime: {surname}</div>
          <div className="label2">Korisničko ime: {username}</div>
          <div className="label2">Datum rođenja: {dateOfBirth}</div>
          <div className="label2">e-mail: {email}</div>
          <div className="label2">Kontakt: {contact}</div>
          <button className="button2" onClick={goToEdit}>Uredi profil</button>
        </>

      );
    }
    else {
      return (
        <><div className="label1">
          Hello, {firstName}!
        </div>
          <div className="label2">Ime: {firstName}</div>
          <div className="label2">Prezime: {surname}</div>
          <div className="label2">Korisničko ime: {username}</div>
          <div className="label2">Datum rođenja: {dateOfBirth}</div>
          <div className="label2">Kontakt: {contact}</div>
          <div className="label2">Trenutni cilj: {currentGoal}</div>
          <button className="button2" onClick={goToEdit}>Uredi profil</button>

          {canChangeGoal() ?
            (<div>
              promjena cilja je omogućena
              <GoalChanger />
            </div>) :
            (<div>
              promjena cilja je onemogućena
            </div>)}
        </>);
    }
  } else {
    return (
      <div>
        <label>
          <Navigate to="/login" replace />
        </label>
      </div>
    );
  }

}

export default UserProfile;

function onChange(event) {
  var data = localStorage;
  const { name, value } = event.target;
  //console.log(value);
  //console.log(JSON.parse(localStorage.getItem("user")).userId.userID)
  const req_body = {
    userID: JSON.parse(localStorage.getItem("user")).userId.userID,
    goalName: value
}
  const options = {
    method: "POST",
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
    },
    body: JSON.stringify(req_body)
  };

  fetch('https://group-fitness-planer-wf93.onrender.com/client/changegoal', options).then(response => {

    if (response.status != 200) {
      response.json().then(json => {
        alert(JSON.stringify(json['message']));
      });
    } else {
      response.json().then((json) => {
        alert("Uspješno se promijenili cilj za idući mjesec!\nVaš novi cilj je " + value)

        //window.location.reload(false);
      })
    }
  });
}

class GoalChanger extends React.Component {
  state = {
    treninzi: []
  };



  componentDidMount() {
    const options = {

      method: "GET",
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
      }
    };

    fetch('https://group-fitness-planer-wf93.onrender.com/registration/goals', options).then(response => {

      if (response.status != 200) {
        response.json().then(json => {
          alert(JSON.stringify(json['message']));
        });
      } else {
        response.json().then((json) => {
          this.setState({ treninzi: json })
          //console.log(data)
          //console.log("ovaj nije bitan -> " + this.state.treninzi)
        })
        //console.log(this.state.treninzi)
      }
    });
  }

  render() {
    return (
      <div>
        <select name="goal" id="goalsselect" onChange={onChange}>
          <option value=""></option>
          {this.state.treninzi.map(goals => (
            <option key={goals} value={goals}>{goals}</option>
          ))}
        </select>
      </div>
    )
  }
} 
