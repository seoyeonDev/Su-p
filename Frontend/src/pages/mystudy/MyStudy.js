import React, { useState } from 'react';
import '../../styles/studygroup/MyStudy.css'
// import axios from 'axios';

function MyStudy() {
    const [activeButton, setActiveButton] = useState('');

    const handleButtonClick = (buttonName) => {
        setActiveButton(buttonName);
        console.log(buttonName);

    };

    return (
        <div className='common-content-container'>
            <div className='common-content'>
                <h1>나의 스터디</h1>

                <div className='tab-buttons'>
                    <button
                        className={`tab-button ${activeButton === '전체 스터디' ? 'active' : ''}`}
                        onClick={() => handleButtonClick('전체 스터디')}
                    >
                        전체 스터디
                    </button>
                    <span>|</span>
                    <button
                        className={`tab-button ${activeButton === '참여중인 스터디' ? 'active' : ''}`}
                        onClick={() => handleButtonClick('참여중인 스터디')}
                    >
                        참여중인 스터디
                    </button>
                    <span>|</span>
                    <button
                        className={`tab-button ${activeButton === '운영중인 스터디' ? 'active' : ''}`}
                        onClick={() => handleButtonClick('운영중인 스터디')}
                    >
                        운영중인 스터디
                    </button>
                    <span>|</span>
                    <button
                        className={`tab-button ${activeButton === '종료된 스터디' ? 'active' : ''}`}
                        onClick={() => handleButtonClick('종료된 스터디')}
                    >
                        종료된 스터디
                    </button>
                </div>

                <div className="attendance-rate">
                    <div> </div>
                       
                    
                </div>

            </div>
        </div>
    );
}

export default MyStudy;
