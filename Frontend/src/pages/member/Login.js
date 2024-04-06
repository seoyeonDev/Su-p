import React, {Component, useState} from 'react';
import axios from "axios";
import {Link} from "react-router-dom";

function Login () {
    // const [message, setMessage] = useState('');
    // useState는 ??
    // constructor(props) {
    //     super(props);
    //     this.state = { test: '' }; // 상태 초기화
    // }
    const [test, setTest] = useState('');
    const loginTest = (() => {
            // axios.post('/login')
            //     .then(() => {
            //         console.log('로그인 성공')
            //     });

            axios.get('/member/test')
                .then(response => {
                    // const data = response.data;
                    setTest(response.data);
                });
        // }
    });


// render() {
        return (
            <div>
                {/*로그인 하는 페이지*/}
                <h1>로그인</h1>
                <div className = "member-text">
                    <input type="text" placeholder={"아이디를 입력하세요."}/><br/>
                    <input type="password" placeholder={"비밀번호를 입력하세요."}/>
                </div>
                <div>
                    <button type="button" onClick={loginTest}>로그인</button>
                    <Link to={"/join"} className={"button-style"}>회원가입</Link>
                </div>
                <div> testMsg : {test}</div>

            </div>
        );
    }
// }

export default Login;