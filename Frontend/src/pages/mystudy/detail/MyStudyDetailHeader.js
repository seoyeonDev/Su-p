// Header.js
import React, {useState, useEffect} from 'react';
import {Link} from 'react-router-dom'; 
import { useNavigate } from 'react-router-dom';
import '../../../styles/studygroup/MyStudy.css';

const MyStudyDetailHeader = ({title, selectedContent, onSelect, isAdmin, group_id}) => {
    const navigate = useNavigate();

    const handleButtonClick = (buttonName) => {
        onSelect(buttonName)
    };

    const handleCreatePost = () => {
        navigate(`/createPost?groupId=${group_id}`);
    }

    return (
        <header className={'common-content'}>
            <h2>{title}</h2>


            <button
                className={`tab-button ${selectedContent === 'HOME' ? 'active' : ''}`}
                onClick={() => handleButtonClick('HOME')}>HOME</button>
            <button
                className={`tab-button ${selectedContent === '소개글 보기' ? 'active' : ''}`}
                onClick={() => handleButtonClick('소개글 보기')}>소개글 보기</button>
            <button
                className={`tab-button ${selectedContent === '전체글 보기' ? 'active' : ''}`}
                onClick={() => handleButtonClick('전체글 보기')}>전체글 보기</button>
            <button
                className={`tab-button ${selectedContent === '내 글 보기' ? 'active' : ''}`}
                onClick={() => handleButtonClick('내 글 보기')}>내 글 보기</button>
            <button
                className={`tab-button ${selectedContent === '나의 출석' ? 'active' : ''}`}
                onClick={() => handleButtonClick('나의 출석')}>나의 출석</button>
            <button onClick={() => handleCreatePost()}>글쓰기</button>
            {isAdmin && (
                <button
                    className={`tab-button ${selectedContent === '스터디 관리' ? 'active' : ''}`}
                    onClick={() => handleButtonClick('스터디 관리')}>
                    스터디 관리
                </button>
            )}

        </header>
    );
};

export default MyStudyDetailHeader;
