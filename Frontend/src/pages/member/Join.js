import React, { useState } from 'react';
import axios from 'axios';
import '../../styles/components/Member.css';


function Join() {
    const [userId, setUserId] = useState('');
    const [idCheckMsg, setIdCheckMsg] = useState('');
    const [password, setPassword] = useState('');
    const [checkPassword, setCheckPassword] = useState('');
    const [passwordCheckMsg, setPasswordCheckMsg] = useState('');

    // id 체크 
    const idChk = async () => {
        try {
            const response = await axios.get(`/member/checkId/${userId}`);
            if (response.data === 0) {
                setIdCheckMsg('사용 가능한 아이디입니다.');
            } else if (response.data === 1) {
                setIdCheckMsg('사용 중인 아이디입니다.');
            }
        } catch (error) {
            console.error(error);
        }
    };

    // 비밀번호 입력
    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    // 비밀번호 확인
    const handleCheckPasswordChange = (e) => {
        setCheckPassword(e.target.value);
        console.log("password:"+password);
        console.log("e.target.value:"+e.target.value);
        if (e.target.value !== password) {
            setPasswordCheckMsg('비밀번호가 일치하지 않습니다.');
        } else {
            setPasswordCheckMsg('비밀번호가 일치합니다.');
        }
    };

    return (
        <div className="content-container">
            <div className="content">
                <h1>회원가입</h1>
                <hr />

                <div>
                    {/* 이미지 */}
                    <div className={"img"} id={"img"}>사진 넣는 부분</div>

                    {/* 가입란 */}
                    <div>
                        <div>
                            <label>아이디</label>
                            <input id="user_id" name={userId} type="text" placeholder={"아이디를 입력하세요."} onChange={(e) => setUserId(e.target.value)} />
                            <button type="button" onClick={idChk}>중복확인</button>
                            <div>{idCheckMsg}</div>

                            {/* onclick="idChk()" 가 아니라 onClick={idChk} 라고 해야 한대  */}
                        </div>
                        <div>
                            <label>비밀번호</label>
                            <input id="password" name={"password"} type="password" placeholder={"비밀번호를 입력하세요."} value={password} onChange={handlePasswordChange} />
                        </div>
                        <div>
                            <label>비밀번호 재확인</label>
                            <input id="check-password" name={"checkPassword"} type="text" placeholder={"비밀번호를 재입력하세요."} value={checkPassword} onChange={handleCheckPasswordChange} />
                            <div>{passwordCheckMsg}</div>
                        </div>
                        <div>
                            <label>이름</label>
                            <input name={"name"} type="text" placeholder={"이름을 입력하세요."} />
                        </div>
                        <div>
                            <label>닉네임</label>
                            <input name={"nickname"} type="text" placeholder={"닉네임을 입력하세요."} />
                            <button type="button">중복확인</button>
                        </div>
                        <div>
                            <label>이메일</label>
                            <input name={"email"} type="text" placeholder={"이메일을 입력하세요."} />
                            <button type="button">인증번호 발송</button>
                        </div>
                        <div>
                            <label>인증번호</label>
                            <input name={"verificationCode"} type="text" placeholder={"인증번호를 입력하세요."} />
                            <button type="button">인증하기</button>
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
                        <button>회원가입</button>
                    </div>
                </div>
            </div>

        </div>
    );

}

export default Join;