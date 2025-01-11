// Content.js
import React from 'react';
import { useNavigate } from 'react-router-dom';
import {getIdFromLocalStorage} from "../Common";

const MyStudyList = ({ selectedContent }) => {
    const navigate = useNavigate();

    const user_id = getIdFromLocalStorage();

    const moveMyStudyDetail = (group_id) => {
        navigate(`/MyStudyDetail/${group_id}/${user_id}`);
    };

    return (
        <div className='study-card-container'>
            {selectedContent.length > 0 ? (
                selectedContent.map((item) => (
                    // console.log(study)
                    <div key={String(item.group_id)} className='study-card'>
                        <button onClick={() => moveMyStudyDetail(item.group_id)}>
                           <h4>{item.title}</h4>
                        </button>

                        <p>{item.user_id}</p>
                        <p>{item.role_nm}</p>
                    </div>
                ))
            ) : (
                <p> 조회된 그룹이 없습니다.</p>
            )}
        </div>
    );
};

export default MyStudyList;
