import React, { useState, useEffect } from 'react';
import axios from 'axios';


const ChangePwd = () => {
  const user_id = localStorage.getItem('loginId');
  const user_name = localStorage.getItem('userName');
  const user_email = localStorage.getItem('userEmail');
  const [newPwd, setNewPwd] = useState('');
  const [chkPwd, setChkPwd] = useState('');
  const [currentPwd, setCurrentPwd] = useState('');

  const vo = {
    user_id : user_id,
    name : user_name,
    email : user_email
  }

  const handleCurrentPassword = (event) => {
    setCurrentPwd(event.target.value);
  }

  const handleNewPassword = (event) => {
    setNewPwd(event.target.value);
  }

  const handleCheckPassword = (event) => {
    setChkPwd(event.target.value);
  }

  const changePassword = () => {
    console.log(vo);
    if(currentPwd !== '' && newPwd !== '' && chkPwd !== '' && (newPwd === chkPwd)) {
      axios.post(`http://localhost:8080/member/changePwd?currentPassword=${currentPwd}&newPassword=${chkPwd}`, vo)
          .then(response => {
              console.log(response.data);
              if(response.data === 1) {
                alert('비밀번호가 변경되었습니다.');
              } else {
                alert('현재 비밀번호가 일치하지 않습니다.');
              }
          });
    } else if(currentPwd === '' && (newPwd === chkPwd)) {
      alert('현재 비밀번호를 입력해주세요.');
    } else if(currentPwd !== '' && (newPwd === '' || chkPwd === '')) {
      alert('변경할 비밀번호를 입력해주세요.');
    } else if(currentPwd !== '' && (newPwd !== chkPwd)) {
      alert('입력한 비밀번호가 일치하지 않습니다. 다시 시도해주세요.');
    }
  };
  
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
            <input type='password' id='currentPwd' name='currentPwd' onChange={handleCurrentPassword} /> <br/>
            <input type='password' id='newPassword' name='newPassword' onChange={handleNewPassword} /> <br/>
            <input type='password' id='checkPassword' name='checkPassword' onChange={handleCheckPassword} />
        </div>
      </div>
      <div className='changePwd-area-change'>
        <button onClick={changePassword}>수정</button>
      </div>
    </div>
  );
};

export default ChangePwd;