import React, { useState } from 'react';
import axios from 'axios';
import {Link, useNavigate} from "react-router-dom";
import {Cookies, useCookies} from 'react-cookie';



function Login () {


    axios.defaults.withCredentials = true;

    // state 변수 선언
    // const [test , setTest] = useState('');
    const [userId, setUserId] = useState('');
    const [userPwd,setUserPwd] = useState('');
    const [msg, setMsg] = useState('');
    const navigate = useNavigate();
    const [cookies, setCookie] = useCookies(['user_id']);

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


    const postLogin = () => {
        console.log('login button')
        axios.post('http://localhost:3000/member/login',
                memberVo
            )
            .then(response => {
                if (response.status === 200 && response.data.msg === 'unlocked') {
                    setMsg(response.data.msg);
                    // setUserId(response.data);
                    // console.log('로그인 완료 , ', msg);
                    alert('로그인 성공 ! ' + userId + ' 님');
                    localStorage.setItem("user_id", userId);
                    // setCookie("user_id",response.data.user_id);
                    setCookie("user_id", userId);
                    // alert(localStorage.getItem("user_id"));
                    navigate('/')
                } else {
                    setMsg(response.data.msg);
                    alert(response.data.msg);
                }
            })
            .catch((err) => {
                console.log('에러 , ', err);
            })
    }


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

    const logout = () => {
        axiosInstance.post('http://localhost:8080/member/logout')
            .then(response => {
                alert('logout 완료 2');
            })
    }



    // const loginTest = (() => {
    //         // axios.post('/login')
    //         //     .then(() => {
    //         //         console.log('로그인 성공')
    //         //     });
    //
    //         axios.get('/member/test')
    //             .then(response => {
    //                 // const data = response.data;
    //                 setTest(response.data);
    //             });
    //     // }
    // });


// render() {
    return (
        <div>
            {/*로그인 하는 페이지*/}
            {/*<h1>로그인</h1>*/}
            <div className = "menuTitle">로그인</div>
            <div className = "member-input">
                <input type="text" id="userId" name={"userId"} placeholder={"아이디를 입력하세요."} onChange={handleUserIdChange}/><br/>
                <input type="password" id="userPwd" name={"userPwd"} placeholder={"비밀번호를 입력하세요."} onChange={handleUserPwdChange}/>
            </div>
            <div>
                <button type="button" onClick={postLogin}>로그인</button><br/>
                <Link to={"/join"} className={"button-style"}>회원가입</Link><br/>
                <Link to={"/findId"}>아이디 찾기</Link><br/>
                <Link to={"/findPwd"}>비밀번호 찾기</Link><br/>
                <button type={"button"} onClick={logout}>로그아웃</button>
            </div>


            <div >
                {msg && <div>{msg.toString()}</div>}
            </div>
        </div>
    );
}
// }

export default Login;