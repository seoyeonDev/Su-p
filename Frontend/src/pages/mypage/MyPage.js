import React, { Component } from 'react';
import { Routes, Route, Navigate  } from 'react-router-dom';
import Left from './Left';
import Info from './Info';
import ChangePwd from './ChangePwd';
import MemberOut from './MemberOut';

const MyPage = () => {
  return (
      <div className="my-page">
        <Left />
        <div className="content">
          <Routes>
            <Route path="/" element={<Navigate to="info" />} />
            <Route path="info" element={<Info/>} />
            <Route path="changepwd" element={<ChangePwd/>} />
            <Route path="memberout" element={<MemberOut/>} />
          </Routes>
        </div>
      </div>
  );
};

export default MyPage;