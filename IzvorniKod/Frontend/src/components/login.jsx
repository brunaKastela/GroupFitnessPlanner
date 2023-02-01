import React, { useState } from "react";
import { Link, Navigate, useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { LOGIN } from "../redux/actionTypes";
import '../static/forms.css';
import '../static/style.css';

export default function Login() {
    const navigate = useNavigate();

    const dispatch = useDispatch();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");



    const onChange = (event) => {

        const { name, value } = event.target;
        setUsername(name);
    };



    const onSubmit = (e) => {
        e.preventDefault();

        const req_body = {
            username: username,
            password: password
        }

        const options = {
            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
            },
            body: JSON.stringify(req_body),
        };


        //required!!
        if (username.trim() === "") {
            alert("Username field is required!");
        } else if (password.trim() === "") {
            alert("Password field is required!");
        }

        fetch(`https://group-fitness-planer-wf93.onrender.com/login/user`, options).then((response) => {
            if (response.status != 200) {
                console.log(response.json().then(json => {
                    alert(json['message']);
                }));
            } else {
                response.json().then((json) => {
                    console.log(json);
                    localStorage.setItem(
                        "user",
                        JSON.stringify({
                            userId: json,
                            password:password,
                            ...{
                                authdata: window.btoa(
                                    `${req_body.username}:${req_body.password}`
                                ),
                            },
                        })
                    );

                    localStorage.setItem("userType", json['role']);
                    localStorage.setItem("userID", json['userID']);
                    
                    dispatch({
                        type: LOGIN,
                        id: json,
                        logged: true,
                        userType: json['role']
                    });

                    

                    console.log(localStorage);
                    console.log(localStorage.getItem("user"));
                    console.log("tu sam");
                    navigate("/userProfile");
                });
            }
        });
    };

    return (
        <div clasName="form-container">
            <div className="form">
                <div>
                    {JSON.parse(JSON.parse(localStorage.getItem("persist:root")).user).logged &&
                        <Navigate replace to="/userProfile" />}
                </div>
                <form>
                    <div className="input-container">
                        <label>Korisničko ime: </label>
                        <input type="username" name={"username"} value={username} onInput={e => setUsername(e.target.value)}
                            placeholder="Upišite svoj username" required />

                    </div>
                    <div className="input-container">
                        <label>Lozinka: </label>
                        <input type="password" name={"password"} value={password} onInput={e => setPassword(e.target.value)}
                            placeholder="Upišite svoju lozinku" required />

                    </div>
                    <div className="text-center">
                        <label >Nemate račun? <Link to="/registration">Registrirajte se</Link></label>
                    </div>
                    <div className="button-holder">
                        <input type="submit" onClick={onSubmit} value="PRIJAVA"/>
                    </div>
                </form>
            </div>
        </div>
    );
}