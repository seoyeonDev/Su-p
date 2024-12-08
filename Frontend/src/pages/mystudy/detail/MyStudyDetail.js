import React, {useState} from 'react';
import { Routes, Route  } from 'react-router-dom';
import axios from "axios";
import {Link} from 'react-router-dom'; 

import MyStudyDetailHeader from "./MyStudyDetailHeader";
import myStudyDetailHome from "./MyStudyDetailHome";
import MyStudyDetailHome from "./MyStudyDetailHome";
import Test from "../Test";
import MyStudyDetailMyList from "./MyStudyDetailMyList";
import MyStudyDetailGroupInfo from "./MyStudyDetailGroupInfo";
import MyStudyDetailAllList from './MyStudyDetailAllList';
import PostDetail from '../../studylogs/PostDetail';

const MyStudyDetail = ({group_id, user_id}) => {
    const [selectedContent, setSelectedContent] = useState('HOME');

    const [selectedPostId, setSelectedPostId] = useState('');
    const [selectAuthorId, setSelectAuthorId] = useState('');



    // 게시글 상세보기 클릭 시 호출되는 함수
    const handlePostSelect = (postId, authorId, content) => {
        setSelectedPostId(postId); 
        setSelectAuthorId(authorId);
        setSelectedContent(content);
    };

    // 탭 변경될 때, selectedPostId를 null로 설정하여 상세보기 내용 숨기기
    const handleContentChange = (content) => {
        setSelectedContent(content);
        setSelectedPostId(null);
    };

    const BackButton = ({selectedContent, handleContentChange}) => {
        if (selectedContent === 'StudyLogsAllList') {
            return (
                <Link to="/MyStudyDetail">
                    <button onClick={() => handleContentChange('전체글 보기')}>뒤로가기</button>
                </Link>
            );
        }
    
        if (selectedContent === 'StudyLogsMyList') {
            return (
                <Link to="/MyStudyDetail">
                    <button onClick={() => handleContentChange('내 글 보기')}>뒤로가기</button>
                </Link>
            );
        }
    
        return null;
    };

    // const getGroupInfo = async(group_id, user_id) => {
    //     console.log('getGroupInfo');
    //     axios.get('http://localhost:8080/studygroup/studyDetail',{
    //         params : {
    //             group_id : '2411020001',
    //             user_id : user_id   // 그룹장일 경우 다른 헤더 반환
    //         }
    //     }) // 그룹 정보
    //         .then (response => {
    //             if (response.status === 200){
    //                 alert('그룹 정보를 가져왔습니다');
    //                 setSelectedContenct(response.data);
    //             }else {
    //                 alert(response.data);
    //             }
    //         })
    //
    // }


    // const handleSelect = async(buttonName) => {
    //     try {
    //         alert('handleSelect')
    //         await getGroupInfo();
    //     } catch (error){
    //         console.log('ERROR MyStudyDetail : ', error);
    //     }
    // }





    return (
        <div className={'common-content-container'}>
            <div className={'common-content'}>
                <h1>나의 스터디</h1>
                <MyStudyDetailHeader title="스터디명" onSelect={handleContentChange}/>

                <MyStudyDetailHome selectedContent={selectedContent} group_id={group_id} user_id={user_id}/>
                <MyStudyDetailMyList selectedContent={selectedContent} group_id={group_id} onPostSelect={(postId, authorId) => handlePostSelect(postId, authorId, 'StudyLogsMyList')} />
                <MyStudyDetailGroupInfo selectedContent={selectedContent}  group_id={group_id} user_id={user_id}/>
                <MyStudyDetailAllList selectedContent={selectedContent} group_id={group_id} onPostSelect={(postId, authorId) => handlePostSelect(postId, authorId, 'StudyLogsAllList')} />

                {selectedPostId && (
                    <div>
                        <PostDetail selectedContent={selectedContent} postId={selectedPostId} authorId={selectAuthorId} />
                        <BackButton selectedContent={selectedContent} handleContentChange={handleContentChange} />
                    </div>
                )}

            </div>
        </div>

    )
}
export default MyStudyDetail;