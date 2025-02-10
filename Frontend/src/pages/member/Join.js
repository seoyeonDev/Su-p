import React, { useRef, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import '../../styles/components/Member.css';
import {showIconAlert, showMsgAlert} from "../Common.js";


function Join() {
    const navigate = useNavigate();
    const [userId, setUserId] = useState('');
    const [password, setPassword] = useState('');
    const [checkPassword, setCheckPassword] = useState('');
    const [nickname, setNickname] = useState('');
    const [email, setEmail] = useState('');
    const [inputEmailNumber, setInputEmailNumber] = useState('');
    const [file, setFile] = useState('');
    const [selectedImage, setSelectedImage] = useState(null);       // 이미지 미리보기 
    const fileInputRef = useRef();
    const [emailTime, setEmailTime] = useState(10 * 60);            // 이메일 10분
    const [emailSent, setEmailSent] = useState(false);              // 이메일 발송됐을 때 타이머 보이게 만들기

    useEffect(() => {
        if (emailTime > 0) {
            const timer = setTimeout(() => setEmailTime(emailTime - 1), 1000); // 1초 후 실행 
            return () => clearTimeout(timer);
        }
    }, [emailTime])


    // 입력창이 유효한지 체크 
    const [joinCheck, setJoinCheck] = useState({
        checkId: false,
        checkInvalidPwd: false,
        checkpwd: false,
        checkNickname: false,
        checkEmail: false
    })

    const joinCheckAll = (field, value) => {
        setJoinCheck((prevState) => ({
            ...prevState,
            [field]: value,
        }));
    }

    // 메시지 
    const [messages, setMessages] = useState({
        idCheckMsg: '',
        passwordCheckMsg: '',
        pwdValidChk:'',
        nicknameMsg: '',
        emailSendMsg: '',
        emailMsg: '',
        joinMsg: ''
    });

    const setMessage = (key, value) => {
        setMessages((prevMessages) => ({
            ...prevMessages,
            [key]: value,
        }));
    };

    // id 체크 
    const idChk = async () => {
        // 띄어 쓰기 여부 & 5자 이상 
        if (whitespaceCheck(userId)) {
            setMessage('idCheckMsg', '아이디에 공백을 입력할 수 없습니다.');
            joinCheckAll("checkId", false);
        } else if (userId.length < 5) {
            setMessage('idCheckMsg', '아이디는 5자 이상 입력해야 합니다.');
            joinCheckAll("checkId", false);
        } else {
            axios.get(`http://localhost:3000/member/checkId/${userId}`)
                .then(response => {
                    if (response.data === 0) {
                        setMessage('idCheckMsg', '사용 가능한 아이디 입니다.');
                        joinCheckAll("checkId", true);
                    } else if (response.data === 1) {
                        setMessage('idCheckMsg', '이미 사용중인 아이디입니다.');
                        joinCheckAll("checkId", false);
                    }
                }).catch(error => {
                    console.log(error);
                });
        }
    };

    // 비밀번호 입력
    const handlePasswordChange = (e) => {
        let password = e.target.value;

        const regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        setPassword(e.target.value);
        if (whitespaceCheck(password)) {
            setMessage('pwdValidChk', '공백을 입력할 수 없습니다.');
            joinCheckAll("checkInvalidPwd", false);
        } else if (password.length < 8) {
            setMessage('pwdValidChk', '8자 이상 입력해주세요.');
            joinCheckAll("checkInvalidPwd", false);
        } else if (!regex.test(password)) {
            setMessage('pwdValidChk', '영문, 숫자, 특수문자 3가지 이상을 조합하여 비밀번호를 생성해주세요.');
            joinCheckAll("checkInvalidPwd", false);
        } else {
            setMessage('pwdValidChk', '');
            joinCheckAll("checkInvalidPwd", true);
        }
    };

    // 비밀번호 확인
    const handleCheckPasswordChange = (e) => {
        setCheckPassword(e.target.value);
        if (e.target.value !== password) {
            setMessage('passwordCheckMsg', '비밀번호가 일치하지 않습니다.');
            joinCheckAll("checkpwd", false);
        } else {
            setMessage('passwordCheckMsg', '비밀번호가 일치합니다.');
            joinCheckAll("checkpwd", true);
        }
    };

    // 닉네임 중복 검사 
    const nicknameChk = async () => {
        if (whitespaceCheck(nickname)) {
            setMessage('nicknameMsg', '공백을 입력할 수 없습니다.');
            joinCheckAll("nickname", false);
        } else {
            axios.get(`http://localhost:3000/member/checkNickNm/${nickname}`)
                .then(response => {
                    if (response.data === 0) {
                        setMessage('nicknameMsg', '사용 가능한 닉네임입니다.');
                        joinCheckAll("checkNickname", true);
                    } else if (response.data === 1) {
                        setMessage('nicknameMsg', '사용 불가능한 닉네임입니다.');
                        joinCheckAll("checkNickname", false);
                    }
                }).catch(error => {
                    console.log(error);
                });
        }
    }
    // 이메일 인증 
    const emailChk = async () => {
        if (!email.includes("@")) {
            joinCheckAll("email", false);
            setMessage('emailSendMsg', '유효한 이메일을 입력해주세요');
        } else if (whitespaceCheck(email)) {
            joinCheckAll("email", false);
            setMessage('emailSendMsg', '이메일에 공백이 들어갈 수 없습니다.');
        } else {
            axios.post(`http://localhost:3000/mail/mailSend?mail=${email}`)
                .then(response => {
                    console.log(response);
                    if (response.data.success) {
                        joinCheckAll("email", true);
                        setEmailTime(10 * 60);
                        setEmailSent(true);
                        setMessage('emailSendMsg', '이메일로 인증번호를 발송하였습니다.');
                    } else {
                        joinCheckAll("email", false);
                        setMessage('emailSendMsg', '인증번호 발송에 실패하였습니다.');
                    }
                }).catch(error => {
                    console.log(error);
                });
        }
    }
    // 이메일 인증 번호 확인
    const emailNumChk = async () => {
        axios.get(`http://localhost:3000/mail/mailCheck`,{
            params: {
                userNumber : inputEmailNumber
            }
        })
        .then(response => {
            if(response.data === true && (minutes > 0 || seconds > 0)){
                joinCheckAll('checkEmail', true);
                setMessage('emailMsg', '이메일 인증에 성공하였습니다.');
                setEmailSent(false);
            }else if(minutes === 0 && seconds === 0) {
                joinCheckAll('checkEmail', false);
                setMessage('emailMsg', '이메일 유효 시간을 확인해주세요.');
            } else {
                joinCheckAll('checkEmail', false);
                setMessage('emailMsg', '이메일 인증에 실패하였습니다.');
            }
            
        }).catch(error => {
            console.log(error);
        });
    }

    // 가입하기
    const join = async () => {
        const checkbox = document.getElementById('joinChkbox').checked;
        const name = document.getElementById('name').value;

        const data = {
            "user_id": userId,
            "password": password,
            "name": name,
            "nickname": nickname,
            "email": email,
            "join_date": null,
            "profile_img": "프로필 이미지 경로",
            "fail_num": 0,
            "lock_yn": null,
            "authorization": null
        };


        const formData = new FormData();
        formData.append("file", file); // 파일 추가
        formData.append("vo", new Blob([JSON.stringify(data)], { type: "application/json" })); // JSON 데이터 추가


        // 값 유효한지 체크 
        if (!joinCheck.checkId) {
            setMessage('joinMsg', '아이디를 확인해주세요.');
        } else if (!joinCheck.checkpwd || !joinCheck.checkInvalidPwd) {
            setMessage('joinMsg', '비밀번호를 확인해주세요');
        } else if (!joinCheck.checkNickname) {
            setMessage('joinMsg', '닉네임을 확인해주세요');
        } else if (!joinCheck.checkEmail) {
            setMessage('joinMsg', '이메일을 확인해주세요');
        } else if (!checkbox) {
            setMessage('joinMsg', '회원가입 약관에 동의를 확인해주세요');
        } else {
            // 회원가입
            axios.post(`http://localhost:3000/member/join`, formData, {
                headers: {
                    'Content-Type': 'multipart/form-data'
                }
            })
                .then(response => {
                    console.log(response);
                    if (response.status === 200) { // 가입 성공
                        showIconAlert('회원가입 성공', '', 'success');
                        navigate('/login');
                    }
                }).catch(error => {
                    console.log(error);
                });
        }
    }

    // 파일 타입 유효성 검사
    const fileTypes = [
        'image'
    ]
    // 파일 
    const handleFileChange = (event) => {
        const file = event.target.files[0];

        if (file) {
            // 파일 타입 유효성 검사
            const type = file.type;
            if (!fileTypes.includes(type.substring(0,type.indexOf('/')))) {
                event.target.value = '';
                showMsgAlert('프로필 사진 오류', '사진 파일만 첨부가 가능합니다.');
                //alert('사진 파일만 첨부가 가능합니다.');
                return;
            }

            const reader = new FileReader();
            reader.onloadend = () => {
                setSelectedImage(reader.result);
            };
            reader.readAsDataURL(file);
        }

        setFile(file);
    }

    // 파일 삭제 
    const handleFileRemove = () => {
        setFile(null);
        setSelectedImage(null);
        fileInputRef.current.value = "";
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
        <div className="content-container">
            <div className="content">
                <h1>회원가입</h1>
                <hr />

                <div>
                    {/* 이미지 */}
                    <div className={"img"} id={"img"}>
                        <div>
                            <input type="file" ref={fileInputRef} onChange={handleFileChange} />
                            {selectedImage && (
                                <div>
                                    <img src={selectedImage} alt="Selected" style={ {width: '40%', height: 'auto'}}/>
                                </div>
                            )}
                        </div>
                            <button onClick={handleFileRemove}>파일 삭제</button>
                    </div>

                    {/* 가입란 */}
                    <div>
                        <div>
                            <label>아이디</label>
                            <input id="user_id" name={userId} type="text" placeholder={"아이디를 입력하세요."} onChange={(e) => setUserId(e.target.value)} />
                            <button type="button" onClick={idChk}>중복확인</button>
                            <div>{messages.idCheckMsg}</div>

                            {/* onclick="idChk()" 가 아니라 onClick={idChk} 라고 해야 한대  */}
                        </div>
                        <div>
                            <label>비밀번호</label>
                            <input id="password" name={"password"} type="password" placeholder={"비밀번호를 입력하세요."} value={password} onChange={handlePasswordChange} />
                            <div>{messages.pwdValidChk}</div>
                        </div>
                        <div>
                            <label>비밀번호 재확인</label>
                            <input id="check-password" name={"checkPassword"} type="password" placeholder={"비밀번호를 재입력하세요."} value={checkPassword} onChange={handleCheckPasswordChange} />
                            <div>{messages.passwordCheckMsg}</div>
                        </div>
                        <div>
                            <label>이름</label>
                            <input id={"name"} name={"name"} type="text" placeholder={"이름을 입력하세요."} />
                        </div>
                        <div>
                            <label>닉네임</label>
                            <input name={"nickname"} type="text" placeholder={"닉네임을 입력하세요."} value={nickname} onChange={(e) => setNickname(e.target.value)} />
                            <button type="button" onClick={nicknameChk} >중복확인</button>
                            <div>{messages.nicknameMsg}</div>
                            <div>{messages.checkNickname}</div>
                        </div>
                        <div>
                            <label>이메일</label>
                            <input name={"email"} type="text" placeholder={"이메일을 입력하세요."} value={email} onChange={(e) => setEmail(e.target.value)} />
                            <button type="button" onClick={emailChk}>인증번호 발송</button>
                            <div id="emailTimeChk" style={{display: emailSent ? 'block' : 'none'}}>
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

                    {/* 회원 약관 내용 및 체크 */}
                    <div>
                        <div>
                            회원약관동의 내용 생성
                        </div>
                        <div>
                            <input id={"joinChkbox"} type={"checkbox"} /> 위 약관에 동의합니다.
                        </div>
                    </div>

                    <div>
                        <button type="button" onClick={join}>회원가입</button>
                    </div>
                    <div>{messages.joinMsg}</div>
                </div>
            </div>

        </div>
    );

}

export default Join;