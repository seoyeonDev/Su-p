// Header.js
import React, {useState} from 'react';

const MyStudyDetailHeader = ({title, onSelect}) => {

    const [activeButton, setActiveButton] = useState('');
    const handleButtonClick = (buttonName) => {
        // onSelect('select');
        setActiveButton(buttonName);
        console.log(buttonName);

        onSelect(buttonName)

    };

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

        </header>
    );
};

export default MyStudyDetailHeader;
