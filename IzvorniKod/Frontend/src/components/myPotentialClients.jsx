import React, { Component, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import '../static/forms.css';
import '../static/style.css';

export default function MojiKlijenti() {
  const isLoggedIn = useSelector((state) => state.user.logged);
  const navigate = useNavigate();
  const userType = localStorage.getItem("userType");

  useEffect(() => {
    if (!isLoggedIn) {
      navigate("/login");
    }

    //isto kao u table -> trener ovoj stranici ne smije moci pristupiti
    if (userType === "CLIENT") {
      navigate("/userProfile");
    }
  });



  return <MojiKlijentiBezTreninga />
}

class MojiKlijentiBezTreninga extends React.Component {

  state = {
    klijenti: [],
    status: 0
  };

  onClickMy(client) {

    window.location.replace('/addTraining')
    localStorage.setItem("clientID", client.clientID)
    console.log(client.clientID)
  }

  componentDidMount() {
    const userID = localStorage.getItem("userID");

    //console.log(userID)

    const req_body = {
      userID: userID
    }


    const options = {

      method: "POST",
      headers: {
        "Content-Type": "application/json; charset=UTF-8",
      },
      body: JSON.stringify(req_body)
    };


    fetch('https://group-fitness-planer-wf93.onrender.com/coach/potentialclients', options).then(response => {

      if (response.status != 200) {
        response.json().then(json => {
          alert(JSON.stringify(json['message']));
        });
      } else {

        response.json().then((json) => {
          this.setState({ klijenti: json });
          //console.log("ovaj nije bitan -> " + this.state.klijenti)
        })
        //console.log(this.state.klijenti)
      }
    });

  }
  render() {
    console.log(this.state.klijenti)
    var tekst = ""
    if (this.state.klijenti === undefined || this.state.klijenti.length === 0) {
      tekst = "Nema klijenata bez treninga"
    } else {
      tekst = "Klijenti bez dodijeljenog treninga"
    }
    return (
      <div class="site-frame-table">
        <table class="reservation-table">
          <thead>
            <tr>
              <th colSpan="5" >{tekst}</th>
            </tr>
          </thead>

          {this.state.klijenti.map(value => (

            <tbody className="table-section">
              <tr></tr>
              <tr>
                <td width="5%"></td>
                <td width="35%">Ime</td>
                <td width="35%">{value.name}</td>
                <td rowSpan="3" width="20%">
                  <button className="table-button green-button"
                    onClick={() => this.onClickMy(value)}>DODIJELI TRENING</button>
                </td>
                <td width="5%"></td>
              </tr>
              <tr>
                <td width="5%"></td>
                <td width="35%">Prezime</td>
                <td width="35%">{value.surname}</td>
                <td width="5%"></td>
              </tr>
              <tr>
                <td width="5%"></td>
                <td width="35%">Cilj</td>
                <td width="35%">{value.currentGoal.goalName}</td>
                <td width="5%"></td>
              </tr>
              <tr></tr>
            </tbody>
          ))}

        </table>

      </div>
    )
  }
}


