import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';

const InputPassword = () => {
    const user_id = localStorage.getItem('loginId');
    const [password, setPassword] = useState('');
    const navigate = useNavigate();

    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.get('http://localhost:3000/member/inputPassword', {
           params: {
            user_id: user_id,
            password: password
            }
        })
            .then(response => {
                if(response.data) {
                    navigate('/mypage');
                } else {
                    alert('비밀번호가 올바르지 않습니다.');
                }
            });
  };

  return (
    <div className="input-password">
      <h2>비밀번호를 입력하세요</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <input type="password" value={password} onChange={handlePasswordChange} placeholder="비밀번호"/>
        </div>
        <button type="submit">확인</button>
      </form>
    </div>
  );
};

export default InputPassword;