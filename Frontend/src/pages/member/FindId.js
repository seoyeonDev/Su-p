import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {showMsgAlert} from "../Common.js";


function FindId () {
    const [userName, setUserName] = useState('');
    const [userEmail, setEmail] = useState('');
    const [userNumber, setNumber] = useState('');
    const [timer, setTimer] = useState(null);
    const [showUserNumber, setShowUserNumber] = useState(false);
    const [showFindId, setShowFindId] = useState(false);
    const [timeRemaining, setTimeRemaining] = useState(0);

    const handleUserNameChange = (e) => {
        setUserName(e.target.value);
    }

    const handleEmail = (e) => {
        setEmail(e.target.value);
    }

    const handleNumber = (e) => {
        setNumber(e.target.value);
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
                    showMsgAlert("아이디 찾기 성공", response.data + " 입니다.");
                    //alert(response.data + " 입니다.");
                } else if (response.data === '존재하지 않는 회원 정보입니다.'){
                    showMsgAlert("아이디 찾기 실패", response.data);
                    //alert(response.data);
                }
            })
            .catch((err)=> {
                console.log('에러 ', err)
            })
    }

    const emailSend = () => {
        if (!userEmail.includes("@")){
            showMsgAlert("이메일 형식 오류", "유효한 이메일을 입력해주세요.");
            //alert("유효한 이메일을 입력해주세요.");

        } else {
            axios.post(`http://localhost:3000/mail/mailSend?mail=${userEmail}`)
                .then(response => {
                    console.log(response);
                    if(response.data.success){
                        showMsgAlert("이메일 인증", "메일을 발송했습니다.");
                        //alert('메일을 발송했습니다.')
                        setShowUserNumber(true);
                        startTimer();
                    }else {
                        showMsgAlert("이메일 인증", "인증번호 발송에 실패하였습니다.");
                        //alert('인증번호 발송에 실패하였습니다.')
                    }
                })
                .catch(error => {
                    console.log(error);
                })

        }
    }

    const emailChk = () => {
        axios.get(`http://localhost:3000/mail/mailCheck?userNumber=${userNumber}`)
            .then(response => {
                if (response.status === 200 ){
                    if (response.data){
                        showMsgAlert("이메일 인증", "이메일 인증에 성공하였습니다.");
                        //alert('번호가 일치합니다.');
                        setShowFindId(true);
                    } else {
                        console.log(response.data)
                    }
                }
            }).catch(error => {
                console.log(error);
            });
    }

    const formatTimeRemaining = () => {
        const minutes = Math.floor(timeRemaining / 60);
        const seconds = timeRemaining % 60;
        return `${minutes}:${seconds.toString().padStart(2,'0')}`;
    }

    const startTimer = () => {
        const timerDuration = 10*60*1000; // 10 min
        const timer = setTimeout(() => {
            setShowUserNumber(false);
        }, timerDuration);
        setTimer(timer);
        setTimeRemaining(timerDuration / 1000);
    }

    useEffect(() => {
        let interval = null;
        if (showUserNumber){
            interval = setInterval(() => {
                setTimeRemaining((prevTime) => prevTime - 1);
            }, 1000);
        }
        return() => {
            clearTimeout(timer);
            clearInterval(interval);
        };
    }, [showUserNumber, timer]);

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
                <button type="button" onClick={emailSend}>인증번호 발송</button>
            </div>
            {showUserNumber && (
                <div>
                    인증번호
                    <input type={"text"} id={"userNumber"} name={"userNumber"} onChange={handleNumber}/>
                    <button type={"button"} onClick={emailChk}>인증번호 확인</button>
                    <p>남은 시간 : {formatTimeRemaining()}</p>
                </div>
            )}
            {showFindId && (
                <button type="button" onClick={getId}>아이디 찾기</button>
            )}
        </div>
    );
}




export default FindId;
