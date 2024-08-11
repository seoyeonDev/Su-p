import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

function FindPwd() {
    const navigate = useNavigate();
    const [userId, setUserId] = useState('');
    const [userName, setUserName] = useState('');
    const [email, setEmail] = useState('');
    const [inputEmailNumber, setInputEmailNumber] = useState('');
    const [emailTime, setEmailTime] = useState(10 * 60);            // 이메일 10분
    const [emailSent, setEmailSent] = useState(false);              // 이메일 발송됐을 때 타이머 보이게 만들기

    useEffect(() => {
        if (emailTime > 0) {
            const timer = setTimeout(() => setEmailTime(emailTime - 1), 1000); // 1초 후 실행 
            return () => clearTimeout(timer);
        }
    }, [emailTime])

    // 메시지 
    const [messages, setMessages] = useState({
        emailSendMsg: '',
        emailMsg: ''
    });

    const setMessage = (key, value) => {
        setMessages((prevMessages) => ({
            ...prevMessages,
            [key]: value,
        }));
    };


    const emailChk = async () => {
        if (!email.includes("@")) {
            setMessage('emailSendMsg', '유효한 이메일을 입력해주세요');
        } else if (whitespaceCheck(email)) {
            setMessage('emailSendMsg', '이메일에 공백이 들어갈 수 없습니다.');
        } else {
            axios.get(`http://localhost:3000/member/chkPwd`, {
                params: {
                    id: userId,
                    name: userName,
                    email: email
                }
            })
                .then(response => {
                    if (response.data > 0) {
                        sendEmail();
                    } else {
                        setMessage('emailSendMsg', '일치하는 사용자를 찾을 수 없습니다.');
                    }
                })
        }
    }

    // 이메일 인증 
    const sendEmail = async () => {
        axios.post(`http://localhost:3000/mail/mailSend?mail=${email}`)
            .then(response => {
                console.log(response);
                if (response.data.success) {
                    setEmailTime(10 * 60);
                    setEmailSent(true);
                    setMessage('emailSendMsg', '이메일로 인증번호를 발송하였습니다.');
                } else {
                    setMessage('emailSendMsg', '인증번호 발송에 실패하였습니다.');
                }
            }).catch(error => {
                console.log(error);
            });
    }

    // 이메일 인증 번호 확인
    const emailNumChk = async () => {
        axios.get(`http://localhost:3000/mail/mailCheck`, {
            params: {
                userNumber: inputEmailNumber
            }
        })
            .then(response => {
                if (response.data === true && (minutes > 0 || seconds > 0)) {
                    setEmailSent(false);
                    navigate('/memberChangePwd');
                } else if (minutes === 0 && seconds === 0) {
                    setMessage('emailMsg', '이메일 유효 시간을 확인해주세요.');
                } else {
                    setMessage('emailMsg', '이메일 인증에 실패하였습니다.');
                }
            }).catch(error => {
                console.log(error);
            });
    }

    // 공백 검사
    const whitespaceCheck = (value) => {
        if (value.includes(" ")) {
            return true;
        } else {
            return false;
        }
    }

    const minutes = Math.floor((emailTime || 0) / 60);
    const seconds = (emailTime || 0) % 60;

    return (
        <div>
            <h1>비밀번호 찾기</h1>
            <div>
                <label>ID</label>
                <input type="text" name={userId} placeholder={"아이디를 입력하세요."} onChange={(e) => setUserId(e.target.value)} />
            </div>
            <div>
                <label>이름</label>
                <input type="text" name={userName} placeholder={"이름을 입력하세요."} onChange={(e) => setUserName(e.target.value)} />
            </div>
            <div>
                <label>이메일</label>
                <input name={"email"} type="text" placeholder={"이메일을 입력하세요."} value={email} onChange={(e) => setEmail(e.target.value)} />
                <button type="button" onClick={emailChk}>인증번호 발송</button>
                <div id="emailTimeChk" style={{ display: emailSent ? 'block' : 'none' }}>
                    {minutes.toString().padStart(2, '0')}:{seconds.toString().padStart(2, '0')}
                    {/* 이메일 인증 관련 컴포넌트 */}
                </div>
                <div>{messages.emailSendMsg}</div>
            </div>
            <div>
                <label>인증번호</label>
                <input name={"verificationCode"} type="text" placeholder={"인증번호를 입력하세요."} value={inputEmailNumber} onChange={(e) => setInputEmailNumber(e.target.value)} />
                <button type="button" onClick={emailNumChk}>인증하기</button>
                <div>{messages.emailMsg}</div>
            </div>
        </div>
    );
}


export default FindPwd;
