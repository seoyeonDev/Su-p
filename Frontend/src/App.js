import React from "react";
import {BrowserRouter, Route, Routes} from "react-router-dom";

import Header from './components/Header';
import Home from "./routes/Home";
import Join from "./pages/member/Join"
import Login from "./pages/member/Login";
import MemberChangePwd from "./pages/member/ChangePwd";
import AddPost from "./pages/mystudy/CreatePost"

import MyStudy from "./pages/mystudy/MyStudy";
import MyStudyList from "./pages/mystudy/MyStudyList";
import MyStudyHeader from "./pages/mystudy/MyStudyHeader"
import MyStudyDetail from "./pages/mystudy/detail/MyStudyDetail"

import MyPage from "./pages/mypage/MyPage";
import Info from "./pages/mypage/Info";
import ChangePwd from "./pages/mypage/ChangePwd";
import MemberOut from "./pages/mypage/MemberOut";
import InputPassword from "./pages/mypage/InputPassword";

import JoinedList from "./pages/joinedgroup/JoinedList";

import FindId from "./pages/member/FindId";
import FindPwd from "./pages/member/FindPwd";
import StudyList from "./pages/studygroup/StudyList";
import CreateStudygroup from "./pages/studygroup/CreateStudygroup";
import UpdateStudygroup from "./pages/studygroup/UpdateStudygroup";
import StudygroupDetail from "./pages/studygroup/StudygroupDetail";
import CreatePost from "./pages/studylogs/CreatePost"


import './App.css';


function App() {
    return (
        <BrowserRouter>
            <Header/>
            <Routes>
                {/* 헤더 */}
                <Route path="/" element={<Home/>}></Route>
                <Route path="/mystudy" element={<MyStudy/>}></Route>

                {/* 스터디 */}
                <Route path="/addpost" element={<AddPost/>}></Route>

                {/* 멤버 */}
                <Route path="/join" element={<Join/>}></Route>
                <Route path="/login" element={<Login/>}></Route>
                <Route path="/mypage/*" element={<MyPage/>}></Route>
                <Route path="/findId" element={<FindId/>}></Route>
                <Route path="/findPwd" element={<FindPwd/>}></Route>
                <Route path="/inputPassword" element={<InputPassword/>}></Route>
                <Route path="/memberChangePwd" element={<MemberChangePwd/>}></Route>

                {/* 스터디그룹 */}
                {/* <Route path="/studylist" element={<StudyList/>}></Route> */}
                <Route path="/createStudygroup" element={<CreateStudygroup/>}></Route>
                <Route path="/updateStudygroup/:groupId" element={<UpdateStudygroup/>}></Route>
                <Route path="/studygroupDetail/:groupId" element={<StudygroupDetail/>}></Route>

                {/* 가입한 그룹 */}
                <Route path="/joinedlist" element={<JoinedList/>}></Route>
                <Route path="/studyList" element={<StudyList/>}></Route>
                <Route path="/MyStudyDetail/:group_id/:user_id" element={<MyStudyDetail/>}></Route>

                {/*스터디 로그*/}
                <Route path={"/createPost"} element={<CreatePost/>}></Route>
            </Routes>
        </BrowserRouter>
    );
}

export default App;
