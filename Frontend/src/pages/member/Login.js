import React, { Component } from 'react';
import axios from "axios";
import {Link} from "react-router-dom";

class Login extends Component {

    login = () => {
        axios.post('/login')
            .then(() => {
                console.log('로그인 성공')
            });

    };


    render() {
        return (
            <div>
                {/*로그인 하는 페이지*/}
                <h1>로그인</h1>
                <div className = "member-text">
                    <input type="text" placeholder={"아이디를 입력하세요."}/><br/>
                    <input type="password" placeholder={"비밀번호를 입력하세요."}/>
                </div>
                <div>
                    <button type="button" onClick={this.login}>로그인</button>
                    <Link to={"/join"} className={"button-style"}>회원가입</Link>
                </div>

            </div>
        );
    }
}

export default Login;