import React, { useState } from 'react';
import axios from 'axios';

const Info = () => {
    const imageUrl = 'http://localhost:8080/studylogs/getImage/IMG_20180815_144343631.jpg';

    const checkNickNm = () => {
        axios.get('http://localhost:8080/member/checkNickNm')
            .then(() => {
                alert('닉네임 중복체크');
            });
    };

    const changeEmail = () => {
        axios.get('')
            .then(() => {
                alert('이메일 인증번호 발송');
            });
    };

    const checkCode = () => {
        axios.get('')
            .then(() => {
                alert('이메일 인증번호 확인');
            });
    };

    const changeInfo = () => {
        axios.get('')
            .then(() => {
                alert('수정');
            });
    };

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
                <input type='text' id='userId' name='userId' value='sp123' readOnly /> <br/>
                <input type='text' id='userName' name='userName' value='김스프' readOnly /> <br/>
                <input type='text' id='userNickNm' name='userNickNm' placeholder='스프스프' /> <br/>
                <input type='text' id='userEmail' name='userEmail' placeholder='sp123@sp.com' /> <br/>
            </div>
            <div className='info-area-myinfo-invisible'>
                <input type='text' id='verificationCode' name='verificationCode' placeholder='123456' />
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