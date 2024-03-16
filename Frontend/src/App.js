import logo from './logo.svg';
import './App.css';

import { useState, useEffect } from 'react';
import React from "react";

function App() {

  const [message, setMessage] = useState([]);

  useEffect(()=>{
    fetch("/test")
      .then((response)=>{
        return response.json();
      })
      .then((data)=>{
        setMessage(data);
      })
  },[]);

  return (
    <div className="App">
      <header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>

        <div>
          {message}
        </div>
      </header>
    </div>
  );
}

export default App;
