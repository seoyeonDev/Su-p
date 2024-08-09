import React, {useEffect, useState} from 'react';
import axios from 'axios';
import '../styles/components/Header.css';
import '../styles/components/Common.css';
//import { useState, useEffect } from 'react';
import {Cookies, useCookies} from 'react-cookie';
import {useNavigate} from "react-router-dom";

function Header() {
    const [cookies, setCookie, removeCookie] = useCookies(['user_id']);
    const [userId, setUserId] = useState('');
    const navigate = useNavigate();


    // const [userId, setUserId] = useState('');
    //
    // // localStorage Id 가져오기
    // useEffect(()=>{
    //     setUserId(localStorage.getItem("user_id"));
    //
    // })


/*
  const [user, setUser] = useState([]);

  useEffect(() => {
    fetch("/*") // 유저 정보 가져오는 링크 넣기 (getUser) 
      .then((response) => {
        return response.json();
      })
      .then((data) => {
        setUser(data);
      })
  });
*/
    
 const authCheck = ()=> {
     const token = cookies.user_id;
     // axios.post('/users/loginCheck', {token : token})
 }

  const handleLogout = () => { // 서버쪽 /logout으로 보내기

      // alert(localStorage.getItem("user_id") + " 전");
      
      alert(cookies.user_id);
      if (cookies.user_id){
      // if (localStorage.getItem("user_id") != null){
          // alert(localStorage.getItem("user_id"));
          localStorage.removeItem("user_id")
          // alert(localStorage.getItem("user_id"));

          removeCookie("user_id");
          navigate('/');

      } else {
          alert("로그인 상태가 아닙니다. 재로그인해주세요.")

      }

    // axios.post('/logout')
      // .then((response) => {
      //
      //   console.log('로그아웃 성공');
      // })
      // .catch((error) => {
      //   console.error('로그아웃 오류:', error);
      // });
  };

  return (
    <header id="sup-header">
      <h1>
        <a href="/">Su-p</a> 
      </h1>
      <nav>
        <ul>
          {/* 임시로 만들어 놓은 링크, 서버 완성 되면 밑 부분을 쓸거임 */}
          <li className="header-list"><a href="/login">로그인</a></li>
          <li className="header-list"><a href="/mystudy">나의스터디</a></li>
          <li className="header-list"><a href="/inputPassword">마이페이지</a></li>
          <li className="header-list"><div className="logoutBtn" onClick={handleLogout}>로그아웃</div></li>

          {/* 서버에서 유저 정보 가져오는 함수 만든 후 주석 해제 
          {user ? (
            <>
              <li className="header-list"><a href="/mystudy">나의스터디</a></li>
              <li className="header-list"><a href="/mypage">마이페이지</a></li>
              <li className="header-list"><div className="logoutBtn" onClick={handleLogout}>로그아웃</div></li>

            </>
          ) : (
            <>
              <li className="header-list"><a href="/login">로그인</a></li>
            </>
          )}
          */}
        </ul>
      </nav>
    </header>
  );
}

export default Header;