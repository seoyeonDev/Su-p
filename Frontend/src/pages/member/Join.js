import React, {Component} from 'react';
import axios from 'axios';
import '../../styles/components/Member.css';

class Join extends Component {
    idChk = () => {
        axios.post('/idCheck')
            .then(() => {
                console.log('idchk 성공')
            });

    };

    join = () => {
        axios.post('/join')
            .then(() => {
                console.log('회원가입 성공')
            });

    };

    nickNameChk = () => {
        axios.post('/join')
            .then(() => {
                console.log('닉네임 이메일 전송')
            });

    };

    emailChk = () => {
        axios.post('/join')
            .then(() => {
                console.log('닉네임 확인 성공')
            });

    };

    emailCodeChk = () => {
        axios.post('/join')
            .then(() => {
                console.log('회원가입 성공')
            });

    };

    render() {
        return (
            <div>
                <h1>회원가입</h1>

                <div name={"join"} id={"join-template"} >
                    <div className={"img"} id={"img"}>
                        이미지
                    </div>
                    <div id={"join-name"}>
                        아이디             <br/>
                        비밀번호            <br/>
                        비밀번호 재확인        <br/>
                        이름              <br/>
                        닉네임             <br/>
                        이메일             <br/>
                        인증번호            <br/>

                    </div>
                    <div id={"join-input"}>
                        <input name={"id"} type="text" placeholder={"아이디를 입력하세요."}/><br/>
                        <input name={"password"} type="password" placeholder={"비밀번호를 입력하세요."}/><br/>
                        <input name={"passwordCheck"} type="text" placeholder={"아이디를 입력하세요."}/><br/>
                        <input name={"name"} type="password" placeholder={"비밀번호를 입력하세요."}/><br/>
                        <input name={"nickName"} type="text" placeholder={"아이디를 입력하세요."}/><br/>
                        <input name={"email"} type="password" placeholder={"비밀번호를 입력하세요."}/><br/>
                        <input name={"emailChkCode"} type="text" placeholder={"아이디를 입력하세요."}/><br/>

                    </div>
                    <div name={"join"} id={"join-button"}>
                        <button type={"button"} onClick={this.idChk}>중복 확인</button><br/>
                        <br/>
                        <br/>
                        <br/>
                        <button type={"button"} onClick={this.nickNameChk}>중복 확인</button><br/>
                        <button type={"button"} onClick={this.emailChk}>인증번호 발송</button><br/>
                        <button type={"button"} onClick={this.emailCodeChk}>인증하기</button><br/>
                    </div>
                </div>
                <div id={"joinAgreeChk"}>
                    <input id="joinAgreeChk" type={"textarea"}/> <br/>
                    <input id={"joinChkbox"} type={"checkbox"}/> 위 약관에 동의합니다.

                </div>
                <div name={"join"}>
                    <button type={"button"} onClick={this.join}>회원가입</button>
                </div>
            </div>
        );
    }
}

export default Join;