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



export default function CreateTraining(props) {
  const navigate = useNavigate();
  const [training, setTraining] = React.useState({ trainingName: '', duration: '', trainingRules: '', spaceAvailable: '', workouts: [], coachId: '', schedule: []});
  const [error, setError] = React.useState('');
  const [todos, setTodos] = useState([]);
  const [tablica3, setTablica3] = useState([]);
  const [tablica2,setTablica] = useState([[1,"Ponedjeljak",[]],
  [2,"Utorak",[]],
  [3,"Srijeda",[]],
  [4,"Četvrtak",[]],
  [5,"Petak",[]],
  [6,"Subota",[]],
  [7,"Nedjelja",[]],])
  const j = 1;

  const tablica = [[1,"Ponedjeljak",[]],
  [2,"Utorak",[]],
  [3,"Srijeda",[]],
  [4,"Četvrtak",[]],
  [5,"Petak",[]],
  [6,"Subota",[]],
  [7,"Nedjelja",[]],];
  const userID = localStorage.getItem("userID");

  const init = async () => {
    await availableWorkouts();
    await availableSchedule(0);
    await availableSchedule(1);
    await availableSchedule(2);
    await availableSchedule(3);
    await availableSchedule(4);
    await availableSchedule(5);
    await availableSchedule(6);
    setTablica(tablica);
    
    console.log("BBBBBB",tablica)
   // console.log(todos)
   // console.log("KAAJ")
  }

  useEffect(() => {
     init();
  }, []);
  useEffect(() => {
    if(sata.pon > 1 ||sata.uto > 1 ||sata.sri > 1 ||sata.cet > 1 ||sata.pet > 1 ||sata.sub > 1 ||sata.ned > 1){
      console.log("SATAA",sata)
      alert("Molim odaberite jedan termin treninga u danu!");
    }
 }, [tablica2]);
  
  const availableWorkouts = async () => {
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

        
        const response = await fetch('https://group-fitness-planer-wf93.onrender.com/coach/workouts', options);

        if (response.status != 200) {
          response.json().then(json => {
            alert(JSON.stringify(json['message']));
          });
          } else {
            const json = await response.json();
                console.log('json', json)
            //    setTodos(json);
                const results = [];
                
                json.forEach((employee) => {
              //    console.log("--------------")
               //   console.log(employee)
               //   console.log(("--------------"))
                  results.push({
                    workoutID: employee.workoutID,
                    workoutName: employee.workoutName,
                    workoutType: employee.workoutType,
                    complete:false
                  }
                 );
                });
                setTodos(json.map(item => ({...item, complete: false})));
                //setTodos(results)
              /*  const newTodos = [...todos]
                for(var i = 0; i < newTodos.length;i++){
                  const todo2 = newTodos.find();
                  todo2.complete = false;
                  console.log(todo2);
                }
                //setTodos(newTodos)*/
                
                
          }
    
  }

  const availableSchedule = async (i) => {
    const req_body = {
            userID: userID,
            dayOfWeek: i+1
        }


        const options = {

            method: "POST",
            headers: {
              "Content-Type": "application/json; charset=UTF-8",
            },  
            body: JSON.stringify(req_body)
        };

        
        const response = await fetch('https://group-fitness-planer-wf93.onrender.com/coach/getavailableschedule', options);

        if (response.status != 200) {
          console.log(response.json().then(json => {
              alert(JSON.stringify(json['message']));
            }));
          } else {
            const json = await response.json();
             //   console.log('json', json,'dan u tjednu',i)
            //    setTodos(json);
            /*    const results = [];
                
                json.forEach((employee) => {
                  results.push({
                    hour:employee,
                    complete: false
                  }
                 );
                });*/
                tablica[i][2] = json.map(item => ({hour:item, complete: false,dow:i+1}));
          }
    
  }

  const toggleTodo = (workoutID) => {
    setTodos(todos => {
      const newTodos = [...todos]
      const todo = newTodos.find(todo => todo.workoutID === workoutID)
      todo.complete = !todo.complete
      return newTodos;
    })
  }

  const funkc1 = (dow,drugi) => {
    setTablica(todos => {
      const newTodos = [...todos]
      const todo = newTodos[dow-1][2].find(a => a.hour == drugi)
      console.log(todo.hour,izracun2(1))
      if(sata.pon < 1 || todo.hour == izracun2(1)){
        todo.complete = !todo.complete
      }
      return newTodos;
    })
  }
  const funkc2 = (dow,drugi) => {
    setTablica(todos => {
      const newTodos = [...todos]
      const todo = newTodos[dow-1][2].find(a => a.hour == drugi)
      if(sata.uto < 1 || todo.hour == izracun2(2)){
        todo.complete = !todo.complete
      }
      return newTodos;
    })
  }
  const funkc3 = (dow,drugi) => {
    setTablica(todos => {
      const newTodos = [...todos]
      const todo = newTodos[dow-1][2].find(a => a.hour == drugi)
      if(sata.sri < 1 || todo.hour == izracun2(3)){
        todo.complete = !todo.complete
      }
      return newTodos;
    })
  }
  const funkc4 = (dow,drugi) => {
    setTablica(todos => {
      const newTodos = [...todos]
      const todo = newTodos[dow-1][2].find(a => a.hour == drugi)
      if(sata.cet < 1 || todo.hour == izracun2(4)){
        todo.complete = !todo.complete
      }
      return newTodos;
    })
  }
  const funkc5 = (dow,drugi) => {
    setTablica(todos => {
      const newTodos = [...todos]
      const todo = newTodos[dow-1][2].find(a => a.hour == drugi)
      if(sata.pet < 1 || todo.hour == izracun2(5)){
        todo.complete = !todo.complete
      }
      return newTodos;
    })
  }
  const funkc6 = (dow,drugi) => {
    setTablica(todos => {
      const newTodos = [...todos]
      const todo = newTodos[dow-1][2].find(a => a.hour == drugi)
      if(sata.sub < 1 || todo.hour == izracun2(6)){
        todo.complete = !todo.complete
      }
      return newTodos;
    })
  }
  const funkc7 = (dow,drugi) => {
    setTablica(todos => {
      const newTodos = [...todos]
      const todo = newTodos[dow-1][2].find(a => a.hour == drugi)
      if(sata.ned < 1 || todo.hour == izracun2(7)){
        todo.complete = !todo.complete
      }
      return newTodos;
    })
  }

  const onChange = (event) => {
 // function onChange(event) {
    const { name, value } = event.target;
 //   setTraining({[name]: value });
 /*   setTraining(oldForm => {
      const newForm = [{ ...oldForm, [name]: value }]
      console.log("AAAAAAAAAAAAAAAA",newForm);
      return newForm;
    })*/
    setTraining(oldForm => {
      return {...oldForm,[name]:value}
    })
    console.log("AAAAAAAAAAAAAAAA",training);
  }

  const izracun = () => {

    const ta = [];
      for(let g = 0;g < 7;g++){
       for(let f = 0;f < tablica2[g][2].length;f++){
         if(tablica2[g][2][f].complete){
           ta.push([tablica2[g][2][f].dow,tablica2[g][2][f].hour])
         }
       }
      }
      console.log(ta);
      return ta;
  }

  const izracun2 = (a) => {

    const ta = [];
      for(let g = 0;g < 7;g++){
       for(let f = 0;f < tablica2[g][2].length;f++){
         if(tablica2[g][2][f].complete && tablica2[g][2][f].dow == a){
           ta.push(tablica2[g][2][f].hour);
         }
       }
      }
      console.log("HHHAAAAAAAAAAAAAAAAA",ta);
      return ta;
  }
  const data = {
    trainingName: training.trainingName,
    duration: training.duration,
    trainingRules: training.trainingRules,
    spaceAvailable: training.spaceAvailable,
    workouts: todos.filter(todo => todo.complete).map(todo => todo.workoutID),
    coachID: userID,
    schedule: izracun()
  };

  const sata = {
    pon: izracun2(1).length,
    uto: izracun2(2).length,
    sri: izracun2(3).length,
    cet: izracun2(4).length,
    pet: izracun2(5).length,
    sub: izracun2(6).length,
    ned: izracun2(7).length,
  }
 

  const onSubmit =(e) => {
    console.log("OVDJEEEE SMO",data);
    e.preventDefault();
    setError("");
    if(data.trainingName.trim().length == 0 ||
    data.spaceAvailable.trim().length == 0 ||
    data.workouts.length == 0 ||
    data.schedule.length == 0 ||
    data.spaceAvailable.trim().charAt(0).localeCompare("9") == 1 ||
    data.spaceAvailable.trim().charAt(0).localeCompare("0") == -1
    )
    {

      alert("Nisu sva potrebna polja popunjena ili su neispravno popunjena!");
    }
else {
 

  const options = {
    method: 'POST',
    headers: {
      "Content-Type": "application/json; charset=UTF-8",
    },
    body: JSON.stringify(data)
  };

fetch('https://group-fitness-planer-wf93.onrender.com/coach/createtraining', options).then(response => {

if (response.status === 400) {
    console.log(response.json().then(json => {
      alert(JSON.stringify(json['message']));
    }));
  } else {
    response.json().then((json) => {
      window.location.reload(false);
      alert("Uspjesno stvoren trening");
      })            
    }
  });

}

  }

//{console.log('to je ovaj', JSON.stringify(todos))}
  return (
    <div className="">
      <div className="form">
        <form onSubmit={onSubmit}>
          <div className="input-container">
            <label>Naziv treninga</label>
            <input name='trainingName' onChange={onChange} value={training.trainingName} />
          </div>
          
          <div className="input-container">
            <label>Upute</label>
            <input name='trainingRules'  placeholder="npr. potrebni rekviziti" onChange={onChange} value={training.trainingRules} />
          </div>
          <div className="input-container">
            <label>Broj slobodnih mjesta</label>
            <input name='spaceAvailable' onChange={onChange} value={training.spaceAvailable} />
          </div>
          <div className="input-container">
            <label>Odaberite termine:</label>
            <div>
              <>
              
                  <div>{tablica[0][1]}</div><div className = "nekaj">{tablica2[0][2].map(a => <Tabe key={a.hour+1} key1={a.hour} key2 ={1} funkc={funkc1} todo={a} />)}</div>
                  <div>{tablica[1][1]}</div><div className = "nekaj">{tablica2[1][2].map(a => <Tabe key={a.hour+2} key1={a.hour} key2 ={2} funkc={funkc2} todo={a} />)}</div>
                  <div>{tablica[2][1]}</div><div className = "nekaj">{tablica2[2][2].map(a => <Tabe key={a.hour+3} key1={a.hour} key2 ={3} funkc={funkc3} todo={a} />)}</div>
                  <div>{tablica[3][1]}</div><div className = "nekaj">{tablica2[3][2].map(a => <Tabe key={a.hour+4} key1={a.hour} key2 ={4} funkc={funkc4} todo={a} />)}</div>
                  <div>{tablica[4][1]}</div><div className = "nekaj">{tablica2[4][2].map(a => <Tabe key={a.hour+5} key1={a.hour} key2 ={5} funkc={funkc5} todo={a} />)}</div>
                  <div>{tablica[5][1]}</div><div className = "nekaj">{tablica2[5][2].map(a => <Tabe key={a.hour+6} key1={a.hour} key2 ={6} funkc={funkc6} todo={a} />)}</div>
                  <div>{tablica[6][1]}</div><div className = "nekaj">{tablica2[6][2].map(a => <Tabe key={a.hour+7} key1={a.hour} key2 ={7} funkc={funkc7} todo={a} />)}</div>
              </>
            </div>
          </div>
          <div className='error'>{error}</div>
              <>
              <label>Odaberite vjezbe:</label>
                {
                  todos.map(todo => <Todo key={todo.workoutId} toggleTodo={toggleTodo} todo={todo} />)
                }
                
                <div>{todos.filter(todo => todo.complete).length} vjezbi odabrano</div>
              </>
          <div className="button-holder">
            <input type="submit" onClick={onSubmit} value="Stvori trening" />
          </div>
        </form>
      </div>
    </div>
  )
}