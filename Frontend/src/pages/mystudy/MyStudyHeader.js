// Header.js
import React, {useState} from 'react';

const MyStudyHeader = ({ title, onSelect }) => {

    const [activeButton, setActiveButton] = useState('');
    const handleButtonClick = (buttonName, joinstatus, role, status) => {
        // onSelect('select');
        setActiveButton(buttonName);
        console.log(buttonName,joinstatus,role,status);

        onSelect(buttonName,joinstatus,role,status)

    };

    return (
        // <header className='common-content-container'>
        <div className='common-content-container'>
            <div className='common-content'>
            <h1>{title}</h1>
            <button
                className={`tab-button ${activeButton === '전체 스터디' ? 'active' : ''}`}
                onClick={() => handleButtonClick('전체 스터디','','','')}>전체 스터디</button>
            <button
                className={`tab-button ${activeButton === '참여중인 스터디' ? 'active' : ''}`}
                onClick={() => handleButtonClick('참여중인 스터디','PERM20','ROLE20','STAT20')}>참여중인 스터디</button>
            <button
                className={`tab-button ${activeButton === '운영중인 스터디' ? 'active' : ''}`}
                onClick={() => handleButtonClick('운영중인 스터디','','ROLE10','STAT20')}>운영중인 스터디</button>
            <button
                className={`tab-button ${activeButton === '종료된 스터디' ? 'active' : ''}`}
                onClick={() => handleButtonClick('종료된 스터디','','','STAT30')}>종료된 스터디</button>
        {/*</header>*/}
            </div>
        </div>
    );
};

export default MyStudyHeader;
