import React, { useEffect, useState } from 'react';
import axios from 'axios';

const MemberOut = () => {
    const [terms, setTerms] = useState('');
    const [agreed, setAgreed] = useState(false);
    const loginId = localStorage.getItem('loginId');

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
        var checkPwd = prompt('비밀번호를 입력해주세요.');
        if (agreed) {
            axios.delete('http://localhost:8080/member/delete/' + 'test2', {
                params: {
                    password: checkPwd
                }
            })
            .then(response => {
                alert(response.data);
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