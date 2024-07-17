import React, { useState } from 'react';
import axios from 'axios';
import {Link, useNavigate} from "react-router-dom";


function FindId () {
    const [userName, setUserName] = useState('');
    const [userEmail, setEmail] = useState('');

    const handleUserNameChange = (e) => {
        setUserName(e.target.value);
    }

    const handleEmail = (e) => {
        setEmail(e.target.value);
    }

    const getId = () => {
        axios.get(`http://localhost:3000/member/findId`,{
            params: {
                name : userName,
                email : userEmail
            }
        })
            .then(response => {
                if(response.status === 200 && response.data !== '존재하지 않는 회원 정보입니다.'){
                    alert(response.data + " 입니다.");
                } else if (response.data === '존재하지 않는 회원 정보입니다.'){
                    alert(response.data);
                }
            })
            .catch((err)=> {
                console.log('에러 ', err)
            })
    }

    return (
        <div>
            <h1>아이디 찾기</h1>
            <div>
                이름
                <input type="text" id="userName" name={"userName"} placeholder={"이름을 입력하세."} onChange={handleUserNameChange} />
            </div>
            <div>
                이메일
                <input type="text" id={"userEmail"} name ={"userEmail"} placeholder={"이메일을 입력하세요."} onChange={handleEmail} />
            </div>
            <button type="button" onClick={getId}>아이디 찾기</button>
        </div>
    );
}




export default FindId;
