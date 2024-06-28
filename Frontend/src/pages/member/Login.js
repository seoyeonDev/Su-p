import React, { useState } from 'react';
import axios from 'axios';
import {Link} from "react-router-dom";



function Login () {

    // state 변수 선언
    // const [test , setTest] = useState('');
    const [userId, setUserId] = useState('');
    const [userPwd,setUserPwd] = useState('');
    const [msg, setMsg] = useState('');

    const memberVo = {
        user_id : userId,
        pwd : userPwd
    }

    const postLogin = () => {
        axios.post('http://localhost:8080/member/login', memberVo)
            .then(response => {
                setMsg(response.data);
                console.log('로그인 완료 , ', msg);
            })
            .catch((err) => {
                console.log('에러 , ', err);
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
                    <input type="text" id={userId} placeholder={"아이디를 입력하세요."}/><br/>
                    <input type="password" id={userPwd} placeholder={"비밀번호를 입력하세요."}/>
                </div>
                <div>
                    <button type="button" onClick={postLogin}>로그인</button>
                    <Link to={"/join"} className={"button-style"}>회원가입</Link><br/>
                    <Link to={"/findId"}>아이디 찾기</Link><br/>
                    <Link to={"/findPwd"}>비밀번호 찾기</Link><br/>
                </div>

            </div>
        );
    }
// }

export default Login;