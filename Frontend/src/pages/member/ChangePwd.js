import axios from 'axios';
import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

function ChangePwd() {
    const location = useLocation();
    const [password, setPassword] = useState('');
    const [checkPassword, setCheckPassword] = useState('');
    const [isMatching, setIsMatching] = useState(true);
    const [pwdMsg, setPwdMsg] = useState('');
    const { userId, email, name } = location.state || {};
    const navigate = useNavigate(); // useNavigate 훅 사용

    // 비밀번호 입력
    const handlePasswordChange = (e) => {
        let password = e.target.value;

        const regex = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        setPassword(e.target.value);

        if (whitespaceCheck(password)) {
            setPwdMsg('공백을 입력할 수 없습니다.');
            setIsMatching(false);
        } else if (password.length < 8) {
            setPwdMsg('8자 이상 입력해주세요.');
            setIsMatching(false);
        } else if (!regex.test(password)) {
            setPwdMsg('영문, 숫자, 특수문자 3가지 이상을 조합하여 비밀번호를 생성해주세요.');
            setIsMatching(false);
        } else {
            setPwdMsg('');
            setIsMatching(true);
        }
    };

    // 공백 검사
    const whitespaceCheck = (value) => {
        if (value.includes(" ")) {
            return true;
        } else {
            return false;
        }
    }

    const changePassword = async () => {
        if (password !== checkPassword) {
            setIsMatching(false);
            setPwdMsg('비밀번호가 일치하지 않습니다.');
            return;
        } else {

            axios.post(`http://localhost:3000/member/changePwd`, {
                user_id: userId,
                password: password,
                name: name,
                email: email
            })
                .then(response => {
                    setIsMatching(false);
                    if (response.data === 1) {
                        setPwdMsg('비밀번호가 성공적으로 변경되었습니다.');
                        setTimeout(() => {
                            navigate('/login'); // 이동할 경로를 지정
                        }, 3000);
                    } else {
                        setPwdMsg('오류가 발생하였습니다.');
                    }
                });
        }
    }

    return (
        <div className={"common-content-container"}>
            <div className={"common-content"}>
                {!isMatching && (
                    <div style={{ color: 'red' }}>{pwdMsg}</div>
                )}
                <div>
                    <label>비밀번호 입력</label>
                    <input className={"common-input"} type="text" name={"password"} placeholder={"비밀번호를 입력하세요."} onChange={handlePasswordChange} />
                </div>
                <div>
                    <label>비밀번호 재입력</label>
                    <input className={"common-input"} type="text" name={"checkPassword"} placeholder={"비밀번호를 재입력하세요."} onChange={(e) => setCheckPassword(e.target.value)} />
                </div>
                <div>
                    <button className={"common-btn"} onClick={changePassword}>비밀번호 변경</button>
                </div>
            </div>
        </div>
    );
}

export default ChangePwd;