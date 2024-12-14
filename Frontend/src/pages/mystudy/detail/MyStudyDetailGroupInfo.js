import React, {Component, useEffect, useState} from 'react';
import PropTypes from 'prop-types';
import axios from "axios";

const MyStudyDetailGroupInfo = ({selectedContent, group_id, user_id}) => {

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


    const getGroupInfo = async(group_id, user_id) => {
        console.log('getGroupInfo');
        axios.get('http://localhost:8080/studygroup/studyDetail',{
            params : {
                group_id : group_id,
                user_id : user_id   // 그룹장일 경우 다른 헤더 반환
            }
        }) // 그룹 정보
            .then (response => {
                if (response.status === 200){
                    // alert('그룹 정보를 가져왔습니다');
                    console.log(response.data);
                    setContent(response.data);

                    setStudydesc(response.data.vo.study_desc);

                }else {
                    alert(response.data);
                    console.log(response.data);
                }
            })

    }
    

    return (
        <div className={'common-content'}>
            {selectedContent === '소개글 보기' && <p>{studydesc}</p>}

        </div>
    );
}


export default MyStudyDetailGroupInfo;