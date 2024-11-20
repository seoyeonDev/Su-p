// Content.js
import React from 'react';
import axios from "axios";

const MyStudyList = ({ selectedContent }) => {



    return (
        // <div className = 'common-content-container'>
        //     <div className='common-content'>
        //         {selectedContent === '전체 스터디' && <p>전체 스터디</p>}
        //         {selectedContent === '참여중인 스터디' && <p>This is 참여중인 스터디</p>}
        //         {selectedContent === '운영중인 스터디' && <p>This is 운영중인 스터디</p>}
        //         {selectedContent === '종료된 스터디' && <p>This is 종료된 스터디</p>}
        //     </div>
        //
        // </div>
        <div className='study-card-container'>
            {selectedContent.map((item) => (
                // console.log(study)
                <div key={item.group_id} className='study-card'>
                    <h4>{item.title}</h4>
                    <p>{item.user_id}</p>
                    <p>{item.role_nm}</p>

                </div>

            ))}
        </div>
    );
};

export default MyStudyList;
