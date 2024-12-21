// Header.js
import React, {useState} from 'react';
import {Link} from 'react-router-dom'; 
import { useNavigate } from 'react-router-dom';
import '../../../styles/studygroup/MyStudy.css';

const MyStudyDetailHeader = ({title, onSelect, isAdmin, group_id}) => {
    const navigate = useNavigate();

    const [activeButton, setActiveButton] = useState('');
    const handleButtonClick = (buttonName) => {
        setActiveButton(buttonName);
        onSelect(buttonName)
    };

    const handleCreatePost = () => {
        navigate(`/createPost?groupId=${group_id}`);
    }

    return (
        <header className={'common-content'}>
            <h2>{title}</h2>
            <button onClick={() => onSelect('content1')}>Content 1</button>
            <button onClick={() => onSelect('content2')}>Content 2</button>



            <button
                className={`tab-button ${activeButton === 'HOME' ? 'active' : ''}`}
                onClick={() => handleButtonClick('HOME')}>HOME</button>
            <button
                className={`tab-button ${activeButton === '소개글 보기' ? 'active' : ''}`}
                onClick={() => handleButtonClick('소개글 보기')}>소개글 보기</button>
            <button
                className={`tab-button ${activeButton === '전체글 보기' ? 'active' : ''}`}
                onClick={() => handleButtonClick('전체글 보기')}>전체글 보기</button>
            <button
                className={`tab-button ${activeButton === '내 글 보기' ? 'active' : ''}`}
                onClick={() => handleButtonClick('내 글 보기')}>내 글 보기</button>
            <button
                className={`tab-button ${activeButton === '나의 출석' ? 'active' : ''}`}
                onClick={() => handleButtonClick('나의 출석')}>나의 출석</button>
            <button onClick={() => handleCreatePost()}>글쓰기</button>
            {isAdmin && (
                <button
                    className={`tab-button ${activeButton === '스터디 관리' ? 'active' : ''}`}
                    onClick={() => handleButtonClick('스터디 관리')}>
                    스터디 관리
                </button>
            )}

        </header>
    );
};

export default MyStudyDetailHeader;
