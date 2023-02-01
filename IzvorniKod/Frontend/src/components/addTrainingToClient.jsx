import React, { Component, useEffect } from "react";
import  { useState } from 'react';

import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import '../static/forms.css';
import '../static/style.css';

export default function Dodavanje() {
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



    return <DodavanjeTreninga/>
}

class DodavanjeTreninga extends React.Component {
   state = {
          disabledButtons:JSON.parse(localStorage.getItem('disabledButtons')) ||[],
          treninzi: [],
          fondSati: null,
          poslano: false,
          odabraniTreninzi:[]
       
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


        fetch('https://group-fitness-planer-wf93.onrender.com/coach/trainings', options).then(response => {
            this.state.status = response.status
            if (response.status != 200) {
                response.json().then((json)=> {
                    alert(JSON.stringify(json['message']));
                 });    
            } else {

                response.json().then((json) => {
                    this.setState({ treninzi: json });
                    console.log(JSON.stringify(response.json))
                })
                console.log(this.state.treninzi)
            }
        });

      

    }


    ubaciTrening(trainingID) {
        this.state.poslano = true;
        this.state.odabraniTreninzi.push(trainingID);
        console.log("treninzi su"+ this.state.odabraniTreninzi);
        const { disabledButtons } = this.state;
        disabledButtons[trainingID] = true;
        this.setState({ disabledButtons });
        localStorage.setItem('disabledButtons', JSON.stringify(disabledButtons));
    }


    handleChange = (event) => {
        this.setState({
          fondSati: event.target.value
        });
        console.log(typeof parseInt(this.state.fondSati))
    }

    onClickMy2(){
      
        window.location.replace('/createtraining')
        
       
            // disable the button
            
    }

    onClickMy3(){
      
        window.location.replace('/myClients')
        
       
            // disable the button
            
    }

    onCancel() {
        localStorage.removeItem("disabledButtons");
        this.state.fondSati = 0;
        this.state.disabledButtons = [];
        window.location.reload(false);
        
    }

    onSubmit() {
        if (parseInt(this.state.fondSati) < 0  || this.state.fondSati === null) {
            alert("Dodijelite sate");
        }else if(typeof parseInt(this.state.fondSati) !== "number"){
            alert("Fond sati mora imati brojčanu vrijednost");
        }else if(this.state.poslano === false){
            alert("Dodijelite barem jedan trening");
        } else {
            const userID = localStorage.getItem("userID");
            const clientID = localStorage.getItem("clientID");
            const req_body = {
                coachID: userID,
                clientID:clientID,
                hoursAvailable:parseInt(this.state.fondSati)
            }
            console.log("dodaj sate za " + JSON.stringify(req_body))
            const options = {

                method: "POST",
                headers: {
                    "Content-Type": "application/json; charset=UTF-8",
                },
                body: JSON.stringify(req_body)
            };
    
    
            fetch('https://group-fitness-planer-wf93.onrender.com/coach/gethoursavailable', options).then(async response => {
                this.state.status = response.status
                console.log(response.status);
                if (response.status != 200) {
                    response.json().then((json)=> {
                        alert(JSON.stringify(json['message']));
          
                     });                
            } else {

                    for (var i = 0; i < this.state.odabraniTreninzi.length; i++) {
                        var trening = this.state.odabraniTreninzi[i];
                        this.assignTrainig(trening);
                        await this.sleep(65);
                    }
                    alert("Treninzi dodijeljeni!");


                }
            });  
            
            //window.location.replace('/myClients')

        }

    }
     sleep = ms => new Promise(r => setTimeout(r, ms));

    async assignTrainig(trainingID){

       


        const userID = localStorage.getItem("userID");
        const clientID = localStorage.getItem("clientID");
        const req_body = {
            coachID: userID,
            clientID:clientID,
            trainingID:trainingID
        }

        

        const options = {

            method: "POST",
            headers: {
                "Content-Type": "application/json; charset=UTF-8",
            },
            body: JSON.stringify(req_body)
        };

        console.log("dodaj trening" + JSON.stringify(req_body));
        fetch('https://group-fitness-planer-wf93.onrender.com/coach/assigntraining', options).then(response => {
            this.state.status = response.status
            console.log(trainingID + " " + response.status);
            if (response.status != 200) {
                response.json().then((json)=> {
                    alert(JSON.stringify(json['message']));
      
                 });    
            }
            else {
  
                //localStorage.removeItem("disabledButtons")

            }
        });

        //DODATAK
        

        
    }

    
    

    render() {
        
        
        
        console.log(this.state.fondSati)
        console.log(this.state.poslano)
        const clientID = localStorage.getItem("clientID")
        const userID = localStorage.getItem("userID")
       
        if (this.state.treninzi === undefined || this.state.treninzi.length === 0) {
            return( 
            <div>
            <div className="label1">Nemate treninge za dodjelu</div>
            <button onClick={() => this.onClickMy2()} className="green-button" >Stvorite trening</button>
            </div>
            )
         } else {
      return(
        <div id="dodavanje">
        <div id ="kuki">
        <table className="label1">
      <thead>
        <tr>
          <th>Ime treninga</th>
          <th></th>
        </tr>
      </thead>
      <tbody >
        {this.state.treninzi.map(training => (
          <tr key={training.trainingID}>
            <td>{training.trainingName}</td>
            <td>
            <td><button onClick={() => this.ubaciTrening(training.trainingID)} id={training.trainingID}  disabled={this.state.disabledButtons[training.trainingID]}  className="table-button green-button" >Dodijeli trening</button></td>
            </td>
          </tr>
        ))}

      </tbody>
    </table>
    <div className="input-container">
            <label className="label1">Dodjeli fond sati</label>
            <input name='dodjelafonda'  onChange={this.handleChange} />
          </div>
    </div>
    <button onClick={() => this.onSubmit()} className="green-button" >POTVRDI ODABIR</button>
    <button onClick={() => this.onCancel()} className="green-button" >PONIŠTI ODABIR</button>
    <button onClick={() => this.onClickMy3()} className="green-button" >VRATI SE</button>
    </div>
      )
        }

    }
}

    
