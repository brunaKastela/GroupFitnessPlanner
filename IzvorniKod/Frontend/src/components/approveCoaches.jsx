import React, { Component, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

export default function ApproveCoaches() {
    const isLoggedIn = useSelector((state) => state.user.logged);
    const navigate = useNavigate();
    const userType = localStorage.getItem("userType");

    useEffect(() => {
        if (!isLoggedIn) {
            navigate("/login");
        }

        //isto kao u table -> trener ovoj stranici ne smije moci pristupiti
        if (userType !== "ADMIN") {
            navigate("/userProfile");
        }
    });



    return <UnapprovedCoaches />
}

class UnapprovedCoaches extends React.Component {

    state = {
        users: []
    };

    onClickMy() {

        window.location.replace('/addTraining')
    }

    componentDidMount() {
        const userID = 1

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


        fetch('https://group-fitness-planer-wf93.onrender.com/admin/users', options).then(response => {

            if (response.status != 200) {
                console.log(response.json().then(json => {
                    alert(JSON.stringify(json['message']));
                }));
            } else {

                response.json().then((json) => {
                    this.setState({ users: json });
                    console.log(this.state.users)
                })
                //console.log(this.state.treninzi)
            }
        });

    }

    isUnverifiedCoach(value) {
        if(value.role == "COACH") {
            if(value.verified == false) {
                return true;
            }
        } 
        return false;
    }

    deleteAccount(userID, username) {

        if (window.confirm("Jeste li sigurni da želite obrisati korisnika " + username + "?")) {
            const req_body = {
                adminID: 1,
                userID: userID
            }

            const options = {

                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                body: JSON.stringify(req_body)
            };

            fetch('https://group-fitness-planer-wf93.onrender.com/admin/deleteuser', options).then(response => {

                if (response.status != 200) {
                    console.log(response.json().then(json => {
                        alert(JSON.stringify(json['message']));
                    }));
                } else {
                    response.json().then((json) => {
                        window.location.reload(false);
                    })
                }
            });
        }
    }

    verifyCoach(userID, username) {

        if (window.confirm("Jeste li sigurni da želite potvrditi trenera " + username + "?")) {
            const req_body = {
                adminID: 1,
                userID: userID
            }

            const options = {

                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                body: JSON.stringify(req_body)
            };

            fetch('https://group-fitness-planer-wf93.onrender.com/admin/verifycoach', options).then(response => {

                if (response.status != 200) {
                    console.log(response.json().then(json => {
                        alert(JSON.stringify(json['message']));
                    }));
                } else {
                    response.json().then((json) => {
                        window.location.reload(false);
                    })
                }
            });
        }
    }

    render() {
        console.log(this.state.users)
        return (
            <div class="site-frame-table">
                <table class="reservation-table">
                    <thead>
                        <tr>
                            <th colSpan="5" >Korisnici</th>
                        </tr>
                    </thead>

                    {this.state.users.map(value => (

                        <tbody className="table-section">
                            <tr></tr>
                            <tr>
                                <td width="5%"></td>
                                <td width="35%">Korisničko ime</td>
                                <td width="35%">{value.username}</td>
                                <td rowSpan="3" width="20%">
                                    <button className="table-button red-button"
                                        onClick={() => this.deleteAccount(value.userID, value.username)}>OBRIŠI RAČUN</button>
                                </td>
                                <td width="5%"></td>
                            </tr>
                            <tr>
                                <td width="5%"></td>
                                <td width="35%">Ime</td>
                                <td width="35%">{value.name}</td>
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
                                <td width="35%">Uloga</td>
                                <td width="35%">{value.role}</td>
                                { this.isUnverifiedCoach(value) && (
                                <td rowSpan="2" width="20%">
                                    <button className="table-button green-button" 
                                        onClick={() => this.verifyCoach(value.userID, value.username)}>POTVRDI TRENERA</button>
                                </td>
                                )}
                                <td width="5%"></td>
                            </tr>
                            <tr>
                                <td width="5%"></td>
                                <td width="35%">Email</td>
                                <td width="35%">{value.email}</td>
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