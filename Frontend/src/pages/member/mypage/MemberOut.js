import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';

const MemberOut = () => {
    const [terms, setTerms] = useState('');
    const [agreed, setAgreed] = useState(false);
    const navigate = useNavigate();
    const user_id = localStorage.getItem('loginId');

    useEffect(() => {
      // withdrawalTerms.txt 파일을 불러와서 내용을 상태에 저장
    fetch('/withdrawalTerms.txt')
        .then(response => response.text())
        .then(text => setTerms(text))
        .catch(error => console.error('Error:', error));
    }, []);

    const changeAgreement = () => {
        setAgreed(!agreed);
    };

    const withdrawal = () => {
        if (agreed) {
            var checkPwd = prompt('비밀번호를 입력해주세요.');

            axios.delete('http://localhost:3000/member/delete/' + user_id, {
                params: {
                    password: checkPwd
                }
            })
            .then(response => {
                if(response.data) {
                    alert('탈퇴가 완료되었습니다.');
                    localStorage.clear();
                    navigate('/home');
                } else {
                    alert('비밀번호가 일치하지 않습니다.');
                }
            });
        } else {
            alert('탈퇴 약관에 동의해주세요.');
        }
    };


    return (
        <div>
            <h2>탈퇴하기</h2>
            <div className='terms-container'>
                <textarea readOnly value={terms} rows={20} cols={80} style={{ width: '800px', height: '300px', resize: 'none' }}></textarea>
            </div>
            <label>
            <input type="checkbox" checked={agreed} onChange={changeAgreement} />
            회원탈퇴 시 주의사항을 모두 확인하였습니다.
            </label>
            <button onClick={withdrawal}>탈퇴하기</button>
        </div>
    );
};

export default MemberOut;