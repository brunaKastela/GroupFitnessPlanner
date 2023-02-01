import React, { Component, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

const contains = (value, rezerviraniTreninzi) => {
    if (rezerviraniTreninzi !== undefined) {
        const match = rezerviraniTreninzi
            .some(item => item['scheduleID']['training']['trainingID'] == value['scheduleID']['training']['trainingID'] &&
                item['timeOfday'] == value['timeOfday'] &&
                item['scheduleID']['date'] == value['scheduleID']['date']);
        //console.log("return value => " + match);
        return match;
    }
}

export default function Schedule() {
    const isLoggedIn = useSelector((state) => state.user.logged);
    const navigate = useNavigate();
    const userType = localStorage.getItem("userType");

    useEffect(() => {
        if (!isLoggedIn) {
            navigate("/login");
        }

        //isto kao u table -> trener ovoj stranici ne smije moci pristupiti
        if (userType !== "CLIENT") {
            navigate("/userProfile");
        }
    });



    return <ScheduleComponent />
}

class ScheduleComponent extends React.Component {


    state = {
        treninzi: []
    };

    componentDidMount() {
        const userID = localStorage.getItem("userID");
        const trainingID = JSON.parse(localStorage.getItem("trainingScheduleID"))["trainingID"];
        //console.log(userID)

        const req_body = {
            userID: userID,
            trainingID: trainingID
        }


        const options = {

            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
            },
            body: JSON.stringify(req_body)
        };


        fetch('https://group-fitness-planer-wf93.onrender.com/client/schedule', options).then(response => {

            if (response.status != 200) {
                response.json().then(json => {
                    alert(JSON.stringify(json['message']));
                  });
            } else {
                response.json().then((json) => {
                    this.setState({ treninzi: json });
                    //console.log("ovaj nije bitan -> " + this.state.treninzi)
                })
                //console.log(this.state.treninzi)
            }
        });

        fetch('https://group-fitness-planer-wf93.onrender.com/client/reservations', options).then(response => {

            if (response.status != 200) {
                response.json().then(json => {
                    alert(JSON.stringify(json['message']));
                  });
            } else {

                response.json().then((json) => {
                    this.setState({ rezerviraniTreninzi: json });

                    //console.log("ovaj nije bitan -> " + this.state.treninzi)
                })
            }
        });

    }

    sendReservation(userID, trainingID, date) {
        const dateArr = Array.from(date)
        date = dateArr[8] + dateArr[9] + "." + dateArr[5] + dateArr[6] + "." + dateArr[0] + dateArr[1] + dateArr[2] + dateArr[3]
        const req_body = {
            userID: userID,
            trainingID: trainingID,
            date: date
        }

        const options = {

            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
            },
            body: JSON.stringify(req_body)
        };

        fetch('https://group-fitness-planer-wf93.onrender.com/client/makereservation', options).then(response => {

            if (response.status != 200) {
                response.json().then(json => {
                    alert(JSON.stringify(json['message']));
                  });
            } else {
                response.json().then((json) => {
                    alert("Uspješno ste rezervirali trening!")
                    window.location.reload(false);
                    //console.log("ovaj nije bitan -> " + this.state.treninzi)
                })
                //navigate("/trainingschedule");
                //console.log(this.state.treninzi)
            }
        });
    }

    render() {
        const userID = localStorage.getItem("userID");
        const trainingID = JSON.parse(localStorage.getItem("trainingScheduleID"))["trainingID"];
        const data = JSON.stringify(this.state.treninzi)
        const dataReserved = JSON.stringify(this.state.rezerviraniTreninzi)
        //console.log("Rezervirani treninzi -> " + dataReserved)
        console.log(data)
        return (
            <div>


                <div className="site-frame-table">

                    <table className="reservation-table">
                        <thead>
                            <tr>
                                <th colSpan="5" >DOSTUPNI TERMINI TRENINGA</th>
                            </tr>
                        </thead>

                        {this.state.treninzi.map(value => (
                            <tbody className="table-section">
                                <tr></tr>
                                <tr>
                                    <td width="5%"></td>
                                    <td width="35%">ime treninga</td>
                                    <td width="35%">{value['scheduleID']['training']['trainingName']}</td>
                                    <td rowSpan="5" width="20%">
                                        <button className="table-button green-button" disabled={contains(value, this.state.rezerviraniTreninzi)}
                                            onClick={() => this.sendReservation(userID, trainingID, value['scheduleID']['date'])}>REZERVIRAJ</button>
                                    </td>
                                    <td width="5%"></td>
                                </tr>
                                <tr>
                                    <td width="5%"></td>
                                    <td >slobodna mjesta</td>
                                    <td >{value['spaceLeft']}</td>
                                    <td width="5%"></td>
                                </tr>
                                <tr>
                                    <td width="5%"></td>
                                    <td >vrijeme</td>
                                    <td >{value['timeOfDay']}:00</td>
                                    <td width="5%"></td>
                                </tr>
                                <tr>
                                    <td width="5%"></td>
                                    <td >datum</td>
                                    <td >{value['scheduleID']['date']}</td>
                                    <td width="5%"></td>
                                </tr>
                                <tr></tr>
                            </tbody>
                        ))}
                    </table>
                </div>
            </div>

        );
    }
}