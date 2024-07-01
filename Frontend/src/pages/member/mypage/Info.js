import React, { useState, useEffect } from 'react';
import axios from 'axios';

const Info = () => {
    const imageUrl = 'http://localhost:8080/studylogs/getImage/IMG_20180815_144343631.jpg';
    const [userInfo, setUserInfo] = useState(null);
    const [editNickNm, setEditNickNm] = useState('');
    const [editEmail, setEditEmail] = useState('');
    const [isEditing, setIsEditing] = useState(false);
    const user_id = localStorage.getItem('loginId');

    useEffect(() => {
        const fetchData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/member/mypage', {
                    params: {
                        user_id: user_id
                    }
                });
                setEditNickNm(1);
                setEditEmail(1);
                setUserInfo(response.data);
                console.log(response.data);
            } catch(error) {
                console.log("error: " + error);
            }
        };

        fetchData();
    }, []);

    const checkNickNm = () => {
        axios.get('http://localhost:8080/member/checkNickNm')
            .then(response => {
                if(response.data.msg === 0) {
                    setEditNickNm(1);
                } else {
                    setEditNickNm(0);
                }
                alert('닉네임 중복체크');
            });
    };

    const changeEmail = () => {
        setIsEditing(true);
        setEditEmail(0);
        // axios.post('http://localhost:8080/mail/mailSend')
        // .then(response => {
        //     alert('이메일 인증번호 확인');
        // });
    };

    const checkCode = () => {
        axios.post('http://localhost:8080/mail/mailCheck')
            .then(response => {
                if(response.data) {
                    setEditEmail(1);
                    alert('이메일 인증번호 확인');
                } else {
                    setEditEmail(0);
                }
            });
    };

    const changeInfo = () => {
        console.log("editEmail: " + editEmail);
        if(editNickNm === 1 && editEmail === 1) {
            // axios.get('')
            //     .then(() => {
            //         alert('수정');
            //     });
            alert('수정')
        } else {
            alert('수정실패');
        }
    };

    if (!userInfo || !userInfo.member) {
        return <div>No user info available</div>;
    }

    // userInfo가 null이 아니고, userInfo.member도 존재할 때 구조 분해 할당
    const { member } = userInfo;

  return (
    <div>
      <h2>내 정보</h2>
      <div className='info-area'>
        <div className='info-area-image'>
            <img src={imageUrl} style={{ width: '80%', height: 'auto' }}/>
        </div>

        <div className='info-area-type'>
            <div className='info-area-type-visible'>
                아이디  <br/>
                이름    <br/>
                닉네임  <br/>
                이메일  <br/>
            </div>
            <div className='info-area-type-invisible'>
                인증번호
            </div>
        </div>

        <div className='info-area-myinfo'>
            <div className='info-area-myinfo-visible'>
                <input type='text' id='userId' name='userId' value={member.user_id} readOnly /> <br/>
                <input type='text' id='userName' name='userName' value={member.name} readOnly /> <br/>
                <input type='text' id='userNickNm' name='userNickNm' defaultValue={member.nickname} /> <br/>
                <input type='text' id='userEmail' name='userEmail' defaultValue={member.email} /> <br/>
            </div>
            <div className='info-area-myinfo-invisible'>
                <input type='text' id='verificationCode' name='verificationCode' disabled={!isEditing} />
            </div>
        </div>

        <div className='info-area-button'>
            <div className='info-area-button-visible'>
                <br/>
                <br/>
                <button onClick={checkNickNm}>중복확인</button> <br/>
                <button onClick={changeEmail}>변경</button> <br/>
            </div>
            <div className='info-area-button-invisible'>
                <button onClick={checkCode}>인증하기</button> <br/>
            </div>
        </div>

      </div>
      <div className='info-area-change'>
        <button onClick={changeInfo}>수정</button>
      </div>
    </div>
  );
};

export default Info;