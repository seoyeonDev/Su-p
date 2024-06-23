import React from 'react';
import axios from 'axios';

const changePwd = () => {
    axios.get('')
        .then(() => {
            alert('비밀번호 수정');
        });
};

const ChangePwd = () => {
  return (
    <div>
      <h2>비밀번호 변경</h2>
      <div className='changePwd-area'>
        <div className='changePwd-type'>
            현재 비밀번호 입력  <br/>
            새 비밀번호 입력    <br/>
            새 비밀번호 재확인
        </div>
        
        <div className='changePwd-input'>
            <input type='password' id='currentPwd' name='currentPwd' /> <br/>
            <input type='password' id='newPassword' name='newPassword' /> <br/>
            <input type='password' id='checkPassword' name='checkPassword' />
        </div>
      </div>
      <div className='changePwd-area-change'>
        <button onClick={changePwd}>수정</button>
      </div>
    </div>
  );
};

export default ChangePwd;