import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";

import Header from './components/Header';
import Home from "./routes/Home";
import Login from "./pages/member/Login";
import AddPost from "./pages/mystudy/CreatePost"
import MyStudy from "./pages/mystudy/MyStudy";
import MyPage from "./pages/member/MyPage";

import './App.css';


function App() {
 return (
   <BrowserRouter>
      <Header />
      <Routes>
        {/* 헤더 */}
        <Route path="/" element={<Home/>}></Route>
        <Route path="/mystudy" element={<MyStudy/>}></Route>

        {/* 스터디 */} 
        <Route path="/addpost" element={<AddPost/>}></Route>

        {/* 멤버 */}
        <Route path="/login" element={<Login/>}></Route>
        <Route path="/mypage" element={<MyPage/>}></Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;
