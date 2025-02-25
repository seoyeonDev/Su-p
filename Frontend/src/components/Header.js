import React, {useEffect, useState} from 'react';
import axios from 'axios';
import '../styles/components/Header.css';
import '../styles/components/Common.css';
//import { useState, useEffect } from 'react';
import {Cookies, useCookies} from 'react-cookie';
import {useNavigate} from "react-router-dom";
import {getIdFromLocalStorage} from "../pages/Common";
import {showIconAlert} from "../pages/Common.js";


function Header() {
    const [cookies, setCookie, removeCookie] = useCookies(['user_id']);
    const [userId, setUserId] = useState('');
    const navigate = useNavigate();


    // localStorage Id 가져오기
    useEffect(() => {
        setUserId(getIdFromLocalStorage());
        // console.log("useruser "+ userId)
        getLoginExpireChk("user_id");
        getUserId("user_id");
    })

    const getLoginExpireChk = (key) => {
        const item = localStorage.getItem(key);
        if (!item) return null;

        const {value, expireTime} = JSON.parse(item);
        const timeLeft = (expireTime - Date.now())/1000/60;
        console.log(timeLeft + " expire")
        // if ()
        return value;
    }

    const getUserId = (key) => {
        const item = localStorage.getItem(key);
        if (!item) return null;

        const {value, expireTime} = JSON.parse(item);
        return value;
    }


    // 만료 여부 확인
    const getLoginWithExpireDate = (key) => {
        const item = localStorage.getItem(key);
        if (!item) return null;

        const {value, expireTime } = JSON.parse(item);
        if (Date.now() > expireTime) {
            localStorage.removeItem(key);
            logout();
            showIconAlert("로그인 만료", "로그인이 만료되었습니다. 재로그인 해 주세요.", "warning");
            navigate('/');
            return null;
        }

        return value;
    }

    // 1분마다 만료된 데이터 확인 후 삭제
    setInterval(()=>{
        getLoginWithExpireDate("user_id");
        console.log()
    },60000)


    // const authCheck = () => {
    //     const token = cookies.user_id;
    //     // axios.post('/users/loginCheck', {token : token})
    // }

    const handleLogout = () => { // TODO 서버쪽 /logout으로 보내기
        const user_id = getLoginExpireChk("user_id");
        if (user_id) {
            localStorage.removeItem("user_id");

            // removeCookie("user_id");

            logout();
            showIconAlert("로그아웃 성공", "", "success");
            navigate('/');

        } else {
            alert("로그인 상태가 아닙니다. 재로그인 해 주세요.")
        }

    };

    const logout =() => {
        axios.post(`http://localhost:3000/member/logout`)
            .then(response => {
                console.log(response.data);
            });
    }

    return (
        <header id="sup-header">
            <h1>
                <a href="/">Su-p</a>
            </h1>
            <nav>
                <ul>
                    {/* 임시로 만들어 놓은 링크, 서버 완성 되면 밑 부분을 쓸거임 */}
                    {/* <li className="header-list"><a href="/login">로그인</a></li>
          <li className="header-list"><a href="/mystudy">나의스터디</a></li>
          <li className="header-list"><a href="/inputPassword">마이페이지</a></li>
          <li className="header-list"><div className="logoutBtn" onClick={handleLogout}>로그아웃</div></li> */}


                    {userId ? (
                        <>
                            <li className="header-list"><a href="/mystudy">나의스터디</a></li>
                            <li className="header-list"><a href="/mypage">마이페이지</a></li>
                            <li className="header-list">
                                <div className="logoutBtn" onClick={handleLogout}>로그아웃</div>
                            </li>

                        </>
                    ) : (
                        <>
                            <li className="header-list"><a href="/login">로그인</a></li>
                        </>
                    )}
                </ul>
            </nav>
        </header>
    );
}

export default Header;