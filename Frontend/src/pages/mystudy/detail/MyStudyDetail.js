
import React, {useEffect, useState} from 'react';
import { useParams, useLocation } from 'react-router-dom';
import axios from "axios";
import {Link} from 'react-router-dom'; 

import MyStudyDetailHeader from "./MyStudyDetailHeader";
import MyStudyDetailHome from "./MyStudyDetailHome";
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
    const currentDate = useState(new Date()); // 현재 날짜 (startDate과 비교)

    const location = useLocation();

    useEffect(() => {
        getStudygroupInfo(group_id);

        // URL state에서 전달된 값 처리
        if (location.state) {
            const { activeTab, selectedPostId } = location.state;
            if(activeTab){
                setSelectedContent(activeTab);

                if (selectedPostId) {
                    setSelectedPostId(selectedPostId);
                    handlePostSelect(selectedPostId, user_id, 'StudyLogsMyList');
                }
            }
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [location, group_id, user_id]);

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

        if (selectedContent === 'MyStudyDetailHome') {
            return (
                <Link to={`/MyStudyDetail/${group_id}/${user_id}`}>
                    <button onClick={() => handleContentChange('HOME')}>뒤로가기</button>
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
                console.log(response.data);
                setGroupInfo(response.data.vo);
                console.log(groupInfo+ "detailinfo")
            }

        } catch (error) {
            console.error('API call failed:', error);
        }
    }


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
                <MyStudyDetailHeader title="스터디명" selectedContent={selectedContent} onSelect={handleContentChange} isAdmin={groupInfo ? groupInfo.leader_id === user_id : false} group_id={group_id}/>

                {groupInfo ? (
                    <MyStudyDetailHome selectedContent={selectedContent} groupInfo={groupInfo} group_id={group_id} user_id={user_id} onPostSelect={(postId, authorId) => handlePostSelect(postId, authorId, 'MyStudyDetailHome')}/>
                ) : (
                    <div> Loading... </div>
                )}

                <MyStudyDetailGroupInfo selectedContent={selectedContent} groupInfo={groupInfo}  group_id={group_id} user_id={user_id}/>
                <MyStudyDetailAllList selectedContent={selectedContent} group_id={group_id} onPostSelect={(postId, authorId) => handlePostSelect(postId, authorId, 'StudyLogsAllList')} />
                <MyStudyDetailMyList selectedContent={selectedContent} group_id={group_id} onPostSelect={(postId, authorId) => handlePostSelect(postId, authorId, 'StudyLogsMyList')} />
                <MyStudyDetailMyAttendance selectedContent={selectedContent} group_id={group_id} user_id={user_id} groupInfo={groupInfo}/>

                {selectedPostId && (
                    <div>
                        <PostDetail selectedContent={selectedContent} postId={selectedPostId} authorId={selectAuthorId} groupId={group_id}/>
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