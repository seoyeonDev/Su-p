import React, {Component, useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import axios from "axios";

const MyStudyDetailGroupInfo = ({selectedContent,groupInfo, group_id, user_id}) => {

    const [content, setContent] = useState(['']);   // 전체 그룹정보
    const [studydesc, setStudydesc] = useState(['']);   // 스터디 desc

    useEffect(() => {
        if(selectedContent === '소개글 보기'){
            const fetchGroupInfo = async () => {
                await getGroupInfo(group_id,user_id);
            };
            fetchGroupInfo().then(r => {console.log('성공 @@')});
        }
    }, [group_id, user_id, selectedContent]);

    // 그룹 정보 등록
    const getGroupInfo = async() =>{
        setStudydesc(groupInfo.study_desc);
    }
    

    return (
        <div className={'common-content'}>
            {selectedContent === '소개글 보기' && <p>{studydesc}</p>}

        </div>
    );
}


export default MyStudyDetailGroupInfo;