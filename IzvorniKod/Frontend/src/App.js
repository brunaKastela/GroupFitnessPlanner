import React from "react"
import { BrowserRouter as Router, Routes, Route } from "react-router-dom"
import "./App.css"
import { Provider } from "react-redux";
import { PersistGate } from 'redux-persist/integration/react';
import Navigation from './components/Navigation';
import Login from "./components/login"
import Registration from "./components/registration"
import { store, persistor } from './redux/store';
import UserProfile from "./components/userProfile";
import Table from "./components/table"
import Home from './components/Home';
import Schedule from "./components/schedule";
import MyReservations from "./components/myReservations"
import CreateTraining from "./components/createTraining"
import MojiKlijentiBezTreninga from "./components/myPotentialClients"
import EditProfile from "./components/editProfile"
import ApproveCoaches from "./components/approveCoaches"
import DodavanjeTreninga from "./components/addTrainingToClient"

function App() {

  return (
    /*<BrowserRouter>
      <Routes>
        <Route path="/login" element={<Login />} />
      </Routes>
    </BrowserRouter>*/
    <Provider store = {store}>
      <PersistGate loading = {null} persistor = {persistor}>
        <Router>
          <Navigation />
          <Routes>
            <Route exact path="/" element={<Home />} />
            <Route path='/home' element={<Home />} exact />
            <Route path="/login" element = {<Login />} />
            <Route path="/registration" element = {<Registration />} />
            <Route path="/userProfile" element = {<UserProfile />} />
            <Route path="/assignedtrainings" element = {<Table />} />
            <Route path="/trainingschedule" element = {<Schedule/>} />
            <Route path="/myReservations" element = {<MyReservations/>} />
            <Route path="/createtraining" element = {<CreateTraining/>} />
            <Route path="/myClients" element = {<MojiKlijentiBezTreninga/>} />
            <Route path="/editProfile" element = {<EditProfile/>} />
            <Route path="/users" element = {<ApproveCoaches/>} />
            <Route path="/addTraining" element = {<DodavanjeTreninga/>} />
          </Routes>
        </Router>
      </PersistGate>
    </Provider>
  )
}

export default App