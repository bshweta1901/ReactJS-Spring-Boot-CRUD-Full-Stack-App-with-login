import React from 'react';
import logo from './logo.svg';
import './App.css';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom'
import ListRecordComponent from './components/ListRecordsComponent ';
import HeaderComponent from './components/HeaderComponent';
import FooterComponent from './components/FooterComponent';
import CreateRecordsComponent from './components/CreateRecordsComponent';
import Login from './components/Login';
import Register from './components/Register';

function App() {
  return (
    <div>
        <Router>
              <HeaderComponent />
                <div className="container">
                    <Switch> 
                          <Route path = "/" exact component = {Login}></Route>
                          <Route path = "/records" component = {ListRecordComponent}></Route>
                          <Route path = "/add-records/:id" component = {CreateRecordsComponent}></Route>
                          <Route path = "/register" component = {Register}></Route>
                    </Switch>
                </div>
              <FooterComponent />
        </Router>
    </div>
    
  );
}

export default App;
