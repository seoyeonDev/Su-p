import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import {getIdFromLocalStorage} from '../Common.js';

const MemberOut = () => {
    const [terms, setTerms] = useState('');
    const [agreed, setAgreed] = useState(false);
    const navigate = useNavigate();
    const user_id = getIdFromLocalStorage();

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
                console.log(response.data); 
            
                // 응답이 성공한 경우 (탈퇴 성공)
                if (response.data.success) {
                    alert(response.data.msg);  // TODO. sweet alert로 수정
                    localStorage.clear();
                    navigate('/'); 
                } else {
                    // 탈퇴 불가능한 경우 (비밀번호 오류, 스터디 그룹이 존재하는 경우 등)
                    alert(response.data.msg); // TODO. sweet alert로 수정
                }
            })
            .catch(error => {
                // 네트워크 오류 또는 서버 오류 처리
                console.error('탈퇴 처리 중 오류 발생:', error);
                alert('탈퇴 처리 중 오류가 발생했습니다. 다시 시도해주세요.');
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