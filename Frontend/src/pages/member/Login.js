import React, {useState} from 'react';
import axios from 'axios';
import {Link, useNavigate} from "react-router-dom";
import {Cookies, useCookies} from 'react-cookie';
import {showIconAlert} from "../Common.js";


function Login () {

    axios.defaults.withCredentials = true;

    // state 변수 선언
    const [userId, setUserId] = useState('');
    const [userPwd,setUserPwd] = useState('');
    const [msg, setMsg] = useState('');
    const navigate = useNavigate();
    const [cookies, setCookie] = useCookies(['user_id']);
    const [sessionTime, setSessionTime] = useState(30*60);

    const memberVo = {
        user_id : userId,
        password : userPwd
    }

    const handleUserIdChange = (e) => {
        setUserId(e.target.value);
    };

    const handleUserPwdChange = (e) => {
        setUserPwd(e.target.value);
    };

    // 로컬 스토리지에 저장
    const setLoginWithExpireTime = (key, value) => {
        const expireTime = Date.now() + sessionTime * 1000;
        localStorage.setItem(key, JSON.stringify({value, expireTime}));
    }

    const postLogin = () => {
        console.log('login button')
        axios.post('http://localhost:3000/member/login',
                memberVo
            )
            .then(response => {
                if (response.status === 200 && response.data.msg === 'unlocked') {
                    setMsg(response.data.msg);
                    setLoginWithExpireTime("user_id", userId);
                    showIconAlert("로그인 성공", "", "success");
                    //alert('로그인 성공 ! ' + localStorage.getItem("user_id") + ' ' + getIdFromLocalStorage() + ' 님');
                    navigate('/')
                } else {
                    setMsg(response.data.msg);
                    showIconAlert("로그인 실패", response.data.msg, "error");
                    //alert(response.data.msg);
                }
            })
            .catch((err) => {
                console.log('에러 , ', err);
            })
    }

    // 엔터키 눌렀을 때 로그인 하기
    const handleKeyDown = (e) => {
        if (e.key === 'Enter') {
            postLogin();
        }
    };

    const axiosInstance = axios.create({
        baseURL: 'http://localhost:8080/member/loginChk',
        timeout: 1000,
    })

    // 요청
    axiosInstance.interceptors.request.use(
        (config) => {
            console.log('Request: ', config);
            fetchUserInfo();
            return config;
        },
        (error) => {
            return Promise.reject(error);
        }
    )

    // 응답
    axiosInstance.interceptors.response.use(
        (response) => {
            console.log('Response : ', response);
            return response;
        },
        (error)=> {
            return Promise.reject(error);
        }
    )

    const fetchUserInfo = () => {
        axios.get('http://localhost:8080/member/loginChk',
        { withCredentials: true })
            .then(response => {
                if (response.status === 200){
                    alert(response.data + "  1");
                } else {
                    alert('로그인 체크 실패');
                }
            })

    }

    return (
        <div>
            {/*로그인 하는 페이지*/}
            {/*<h1>로그인</h1>*/}
            <div className = "menuTitle">로그인</div>
            <div className = "member-input">
                <input type="text" id="userId" name={"userId"} placeholder={"아이디를 입력하세요."} onChange={handleUserIdChange} onKeyDown={handleKeyDown} /><br/>
                <input type="password" id="userPwd" name={"userPwd"} placeholder={"비밀번호를 입력하세요."} onChange={handleUserPwdChange} onKeyDown={handleKeyDown} />
            </div>
            <div>
                <button type="button" onClick={postLogin}>로그인</button><br/>
                <Link to={"/join"} className={"button-style"}>회원가입</Link><br/>
                <Link to={"/findId"}>아이디 찾기</Link><br/>
                <Link to={"/findPwd"}>비밀번호 찾기</Link><br/>
            </div>

            <div >
                {msg && <div>{msg.toString()}</div>}
            </div>
        </div>
    );
}

export default Login;