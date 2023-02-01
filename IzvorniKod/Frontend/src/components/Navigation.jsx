import React from "react";
import { useSelector, useDispatch, connect } from "react-redux";
import { logoutUser } from "../redux/actions";
import { Nav } from "react-bootstrap";
import '../static/style.css';

function Navigation(props) {

    const dispatch = useDispatch();
    const loggedUser = useSelector((state) => state.user.logged);
    const userType = localStorage.getItem("userType");

    const logout = () => {
        props.dispatch();
        localStorage.clear();
    }

    /*  <a href = '/home'>
                        GroupFitnessPlanner
                    </a> */

    return (
        <nav class="navigation-container">

            <Nav activeKey="/home">
                <ul class="navigation-bar-ul">
                    <li class="navigation-bar-li navigation-bar-link">
                        {loggedUser ? (
                            userType === "CLIENT" ? (
                                <Nav.Link style={{ color: "white" }} id="8" href="/assignedtrainings">
                                    MOJI TRENINZI
                                </Nav.Link>
                            ) : (
                                userType === "COACH" ? (
                                    <Nav.Link style={{ color: "white" }} id="9" href="/createtraining">
                                        STVORI TRENING
                                    </Nav.Link>
                                ) : (
                                    <Nav.Link style={{ color: "white" }} id="13" href="/users">
                                        PREGLED KORISNIKA
                                    </Nav.Link>
                                ) 
                            )

                        ) : (

                            <Nav.Link style={{ color: "white" }} id="1" href="/home">
                                NASLOVNA
                            </Nav.Link>

                        )}

                    </li>

                    <li class="navigation-bar-li navigation-bar-link">
                        {loggedUser ? (
                            <Nav.Link style={{ color: "white" }} id="2" href="/userProfile">
                                MOJ PROFIL
                            </Nav.Link>

                        ) :
                            (
                                <Nav.Link style={{ color: "white" }} id="3" href="/registration">
                                    REGISTRIRAJ SE
                                </Nav.Link>
                            )}
                    </li>

                    <li class="navigation-bar-li navigation-bar-link">   
                        {loggedUser ? (
                            userType ===  "CLIENT" ? (
                                <Nav.Link style={{ color: "white" }} id = "10" href = "/myReservations">
                                    MOJE REZERVACIJE
                                </Nav.Link>
                             ):(
                                userType ===  "ADMIN" ? (
                                    <Nav.Link style={{ color: "white" }} id = "14" href = "/home">
                           
                                    </Nav.Link>
                                 ):(
                                    <Nav.Link style={{ color: "white" }} id = "11" href = "/myClients">
                                        MOJI KLIJENTI
                                    </Nav.Link>
                                )
                            )

                            ):(

                        <Nav.Link style={{ color: "white" }} id = "12" href = "/home">
                           
                        </Nav.Link>

                        )}
                        
                    </li>


                    <li class="navigation-bar-li navigation-bar-link">
                        {loggedUser ? (
                            <Nav.Link style={{ color: "white" }} id="4" href="/home" onClick={() => logout()}>
                                ODJAVA
                            </Nav.Link>
                        ) : (
                            <Nav.Link style={{ color: "white" }} id="5" href="/login">
                                PRIJAVA
                            </Nav.Link>
                        )}
                    </li>


                    <li class="navigation-bar-li gfp">

                        <Nav.Link style={{ color: "white" }} id="6" href="/home">
                            GROUP FITNESS PLANNER
                        </Nav.Link>


                    </li>
                </ul>
            </Nav>
        </nav>
    );
}

const mapDispatchToProps = (dispatch) => {
    return {
        dispatch: () => dispatch(logoutUser()),
    };
};




export default connect(null, mapDispatchToProps)(Navigation);