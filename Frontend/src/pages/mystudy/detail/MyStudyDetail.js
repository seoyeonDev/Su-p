
import React, {useEffect, useState} from 'react';
import { Routes, Route, useParams  } from 'react-router-dom';
import axios from "axios";
import {Link} from 'react-router-dom'; 

import MyStudyDetailHeader from "./MyStudyDetailHeader";
import myStudyDetailHome from "./MyStudyDetailHome";
import MyStudyDetailHome from "./MyStudyDetailHome";
import Test from "../MyStudy";
import MyStudyDetailMyList from "./MyStudyDetailMyList";
import MyStudyDetailGroupInfo from "./MyStudyDetailGroupInfo";
import MyStudyDetailAllList from './MyStudyDetailAllList';
import PostDetail from '../../studylogs/PostDetail';
import MyStudyAdminBeforeOpen from './MyStudyAdminBeforeOpen';
import MyStudyAdminAfterOpen from './MyStudyAdminAfterOpen';
import MyStudyDetailMyAttendance from "./MyStudyDetailMyAttendance";

const MyStudyDetail = () => {
    const [selectedContent, setSelectedContent] = useState('HOME');

    // authorId = 글쓴이 id, login_id와 비교하기 위해 넣음
    const [selectedPostId, setSelectedPostId] = useState('');
    const [selectAuthorId, setSelectAuthorId] = useState('');

    const { group_id, user_id } = useParams();


    const [groupInfo, setGroupInfo] = useState(null);           // studygroup 정보
    const [currentDate, setCurrentDate] = useState(new Date()); // 현재 날짜 (startDate과 비교)


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
                <Link to={`/MyStudyDetail/${group_id}/${user_id}`}>
                    <button onClick={() => handleContentChange('전체글 보기')}>뒤로가기</button>
                </Link>
            );
        }
    
        if (selectedContent === 'StudyLogsMyList') {
            return (
                <Link to={`/MyStudyDetail/${group_id}/${user_id}`}>
                    <button onClick={() => handleContentChange('내 글 보기')}>뒤로가기</button>
                </Link>
            );
        }
    
        return null;
    };

    const getStudygroupInfo = async (group_id) => {
        try {
            const response = await axios.get('http://localhost:8080/studygroup/studyDetail', {
                params: {
                    group_id: group_id,
                    user_id: user_id,
                },
            });

            if(response.status === 200){
                setGroupInfo(response.data.vo);
            }

        } catch (error) {
            console.error('API call failed:', error);
        }
    }

    useEffect(() => {
        getStudygroupInfo(group_id);
    }, [group_id, user_id]);

    // 날짜 비교 함수
    const isBeforeStartDate = () => {
        
        if (groupInfo && groupInfo.startdate) {
            const startDate = new Date(groupInfo.startdate);
            return currentDate < startDate;
        }
        return false;
    };




    return (
        <div className={'common-content-container'}>
            <div className={'common-content'}>
                <h1>나의 스터디</h1>
                <MyStudyDetailHeader title="스터디명" onSelect={handleContentChange} isAdmin={group_id === user_id}/>

                <MyStudyDetailHome selectedContent={selectedContent} group_id={group_id} user_id={user_id}/>
                <MyStudyDetailMyList selectedContent={selectedContent} group_id={group_id} onPostSelect={(postId, authorId) => handlePostSelect(postId, authorId, 'StudyLogsMyList')} />
                <MyStudyDetailGroupInfo selectedContent={selectedContent}  group_id={group_id} user_id={user_id}/>
                <MyStudyDetailAllList selectedContent={selectedContent} group_id={group_id} onPostSelect={(postId, authorId) => handlePostSelect(postId, authorId, 'StudyLogsAllList')} />

                <MyStudyDetailMyAttendance selectedContent={selectedContent} group_id={group_id} user_id={user_id} groupInfo={groupInfo}/>

                {selectedPostId && (
                    <div>
                        <PostDetail selectedContent={selectedContent} postId={selectedPostId} authorId={selectAuthorId} />
                        <BackButton selectedContent={selectedContent} handleContentChange={handleContentChange} />
                    </div>
                )}

                {/* 날짜 비교 후 페이지 변경 */}
                {isBeforeStartDate() ? (
                    <MyStudyAdminBeforeOpen selectedContent={selectedContent}  group_id={group_id} />
                ) : (
                    <MyStudyAdminAfterOpen selectedContent={selectedContent}  group_id={group_id} />
                )}

            </div>
        </div>

    )
}
export default MyStudyDetail;