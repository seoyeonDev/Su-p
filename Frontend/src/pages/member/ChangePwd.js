import React, { useState } from 'react';

function ChangePwd() {
    const [password, setPassword] = useState('');
    const [checkPassword, setCheckPassword] = useState('');

    const changePassword = async()=>{
        if(password === checkPassword){
            // TODO. 비밀번호 변경
        } else {
            // 비밀번호 불일치 
        }
    }

    return (
        <div>
            <div>
                <label>비밀번호 입력</label>
                <input type="text" name={"password"} placeholder={"비밀번호를 입력하세요."} onChange={(e) => setPassword(e.target.value)} />
            </div>
            <div>
                <label>비밀번호 재입력</label>
                <input type="text" name={"checkPassword"} placeholder={"비밀번호를 재입력하세요."} onChange={(e) =>setCheckPassword(e.target.value)} />
            </div>
            <div>
                <button onClick={changePassword}>비밀번호 변경</button>
            </div>
        </div>
    );
}

export default ChangePwd;