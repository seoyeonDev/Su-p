import React from 'react';
import { Link } from 'react-router-dom';

const Left = () => {
  return (
    <div className="left-menu">
      <ul>
        <li>
          <Link to="/mypage/info">내 정보</Link>
        </li>
        <li>
          <Link to="/mypage/changePwd">비밀번호 변경</Link>
        </li>
        <li>
          <Link to="/mypage/memberOut">탈퇴하기</Link>
        </li>
      </ul>
    </div>
  );
};

export default Left;