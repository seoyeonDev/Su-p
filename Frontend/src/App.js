import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";

import Header from './components/Header';
import Home from "./routes/Home";
import Join from "./pages/member/Join"
import Login from "./pages/member/Login";
import AddPost from "./pages/mystudy/CreatePost"
import MyStudy from "./pages/mystudy/MyStudy";
import MyPage from "./pages/member/MyPage";

import JoinedList from "./pages/joinedgroup/JoinedList";
import FindId from "./pages/member/FindId";
import FindPwd from "./pages/member/FindPwd";
import StudyList from "./pages/studygroup/StudyList";


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
        <Route path="/join" element={<Join/>}></Route>
        <Route path="/login" element={<Login/>}></Route>
        <Route path="/mypage" element={<MyPage/>}></Route>
        <Route path="/findId" element={<FindId/>}></Route>
        <Route path="/findPwd" element={<FindPwd/>}></Route>

        {/* 스터디그룹 */}
        <Route path="/studylist" element={<StudyList/>}></Route>

        {/* 가입한 그룹 */}
        <Route path="/joinedlist" element={<JoinedList/>}></Route>

      </Routes>
    </BrowserRouter>
  );
}

export default App;
