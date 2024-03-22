import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";

import Header from './components/Header';
import Home from "./routes/Home";
import Login from "./pages/member/Login";
import AddPost from "./pages/mystudy/CreatePost"

import './App.css';


function App() {


  return (
    <BrowserRouter>
      <Header />
      <Routes>
        <Route path="/" element={<Home/>}></Route>
        <Route path="/login" element={<Login/>}></Route>
        <Route path="/addpost" element={<AddPost/>}></Route>
      </Routes>
    </BrowserRouter>
  );
}

export default App;
