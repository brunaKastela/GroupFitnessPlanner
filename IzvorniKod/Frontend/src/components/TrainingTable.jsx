import React, { Component } from "react";
import { useNavigate } from "react-router-dom";

//import data from "../static/testJson.json"
//import data from "../static/testJson.json"

const userID = localStorage.getItem("userID");

export default function TrainingTable() {
    const navigate = useNavigate();

    const scheduleTraining = (trainingID) => {
        console.log("trainingID " + trainingID)

        localStorage.setItem(
            "trainingScheduleID",
            JSON.stringify({
                "trainingID": trainingID
            })
        );

        navigate("/trainingschedule")
    }

    return <TrainingTableClass scheduleTraining={scheduleTraining} />
}

class TrainingTableClass extends React.Component {

    state = {
        treninzi: [],
        rezerviraniTreninzi: []
    };

    componentDidMount() {

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


        fetch('https://group-fitness-planer-wf93.onrender.com/client/trainings', options).then(response => {
            if (response.status === 400) {
                response.json().then(json => {
                    alert(JSON.stringify(json['message']));
                  });
            } else {

                response.json().then((json) => {
                    this.setState({ treninzi: json });

                    //console.log("ovaj nije bitan -> " + this.state.treninzi)
                })
            }
        });

    }




    render() {
        //console.log("user id -> " + userID)

        const data = JSON.stringify(this.state.treninzi)
        console.log("treninzi -> " + data)

        return <div>
            <div className="site-frame-table">

                <table className="reservation-table">
                    <thead>
                        <tr>
                            <th colSpan="5" >DOSTUPNI TRENINZI</th>
                        </tr>
                    </thead>

                    {this.state.treninzi.map(value => (
                        <tbody className="table-section">
                            <tr></tr>
                            <tr>
                                <td width="5%"></td>
                                <td width="35%">ime treninga</td>
                                <td width="35%">{value['training']['trainingName']}</td>
                                <td rowSpan="5" width="20%">
                                    <button className="table-button green-button"
                                        onClick={() => this.props.scheduleTraining(value['training']['trainingID'])}>ODABERI</button>
                                </td>
                                <td width="5%"></td>
                            </tr>
                            <tr>
                                <td width="5%"></td>
                                <td >trajanje</td>
                                <td >{value['training']['duration']}</td>
                                <td width="5%"></td>
                            </tr>
                            <tr>
                                <td width="5%"></td>
                                <td >potrebna oprema</td>
                                <td >{value['training']['trainingRules']}</td>
                                <td width="5%"></td>
                            </tr>
                            <tr>
                                <td width="5%"></td>
                                <td >trener</td>
                                <td >{value['coach']['name']} {value['coach']['surname']}</td>
                                <td width="5%"></td>
                            </tr>
                            <tr>
                                <td width="5%"></td>
                                <td >kontakt</td>
                                <td >{value['coach']['email']}</td>
                                <td width="5%"></td>
                            </tr>
                            <tr></tr>
                        </tbody>
                    ))}
                </table>
            </div>
        </div>
    }
}

//export default TrainingTable;