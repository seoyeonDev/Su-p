import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import {getIdFromLocalStorage} from "../Common";

const StudygroupDetail = () => {
    const { groupId } = useParams();
    const navigate = useNavigate();
    const [studyGroupItems, setStudyGroupItems] = useState([]);
    const [studyGroupStatus, setStudyGroupStatus] = useState();
    const [studyGroupStartdate, setStudyGroupStartdate] = useState();
    const [studyGroupEnddate, setStudyGroupEnddate] = useState();
    const [studygroupSubM, setStudygroupSubM] = useState();
    const [studygroupMinCnt, setStudygroupMinCnt] = useState();
    const [studygroupPerm, setStudygroupPerm] = useState();
    const user_id = getIdFromLocalStorage();
    const currentDate = new Date();
    const formattedDate = `${currentDate.getFullYear()}.${String(currentDate.getMonth() + 1).padStart(2, '0')}.${String(currentDate.getDate()).padStart(2, '0')}`;

    useEffect(() => {
        getStudyGroupInfo();
    }, [groupId]);

    /**
     * studygroup 정보 가져오기
     */
    const getStudyGroupInfo = async () => {
        try {
            const response = await axios.get('http://localhost:3000/studygroup/studyDetail', {
                params: {
                    group_id: groupId
                }
            });
            setStudyGroupItems(response.data.vo);
            setStudyGroupStartdate(response.data.vo.startdate.split('T')[0]);
            setStudyGroupEnddate(response.data.vo.enddate.split('T')[0]);
            setStudygroupSubM(response.data.vo.chk_m_nm);
            setStudygroupMinCnt(response.data.vo.chk_min_cnt);
            setStudygroupPerm(response.data.perm);
            setStudyGroupStatus(response.data.vo.status);
        } catch (error) {
            console.error('Error fetching data:', error)
        }
    }


    /**
     * 관리자 전용 - 수정 페이지로 이동
     * @param {string} groupId 
     */
    const handleUpdateGroup = (groupId) => {
        navigate(`/updateStudygroup/${groupId}`);
    };

    /**
     * 관리자 전용 - 그룹 삭제
     * @param {string} groupId 
     * @param {string} leaderId 
     */
    const handleDeleteGroup = async (groupId, leaderId) => {
        try {
            const vo = {
                group_id: groupId,
                leader_id: leaderId
            };

            await axios.delete('http://localhost:3000/studygroup/deleteGroup', {
                data: vo,
            });
            alert('삭제에 성공했습니다.');
            navigate('/');
        } catch(error) {
            console.error('에러: ', error);
            alert('삭제에 실패하였습니다.')
        }
    };

    /**
     * 스터디그룹 가입 취소하기
     * @param {string} groupId 
     * @param {string} user_id 
     */
    const handleCancleJoin = async (groupId, user_id) => {
        console.log("클릭");
        try {
            const response = await axios.delete(`http://localhost:8080/joinedgroup/user/${user_id}/group/${groupId}`);

            if (response.status === 200) {
                getStudyGroupInfo();
            }
        } catch (error) {
            console.error('API call failed:', error);
        }
    }

    /**
     * 스터디그룹 가입하기
     * @param {string} groupId 
     * @param {string} user_id 
     */
    const handleJoinGroup = async (groupId, user_id) => {
        try {
            const response = await axios.post('http://localhost:8080/joinedgroup/join', null,
                {
                    params: {
                        user_id: user_id,
                        group_id: groupId,
                        role_status: 'ROLE20',
                    },
                }
            );
            if (response.data.status === 'success') {
                getStudyGroupInfo();
            }
        } catch (error) {
            console.error('API call failed:', error);
        }
    }
    
    return (
        <div>
            <div>
                <h2>{studyGroupItems.title}</h2>
                <h4>{studyGroupItems.leader_id} &ensp;|&ensp; {formattedDate} &emsp;&ensp; 조회 {studyGroupItems.view_cnt}</h4>
            </div> <br/>
            <div>
                <h4>모집인원 &ensp;&nbsp; {studyGroupItems.mem_cnt}</h4>
                <h4>시작일 &emsp;&ensp; {studyGroupStartdate}</h4>
                <h4>종료일 &emsp;&ensp; {studyGroupEnddate}</h4>
                <h4>제출기준 &emsp;&ensp; {studygroupSubM}</h4>
                <h4>제출횟수 &emsp;&ensp; {studygroupMinCnt}</h4>
            </div>
            <div>
                {studyGroupStatus === 'STAT00' && (
                    <>
                        <button onClick={() => handleUpdateGroup(studyGroupItems.group_id)}>수정</button>
                        <button onClick={() => handleDeleteGroup(studyGroupItems.group_id, studyGroupItems.leader_id)}>삭제</button>
                    </>
                )}
                {studygroupPerm === 'PERM10' && studyGroupStatus === 'STAT10' && <button onClick={() => handleCancleJoin(studyGroupItems.group_id, user_id)}>가입취소</button>}
                {studygroupPerm === 'PERM00' && studyGroupStatus === 'STAT10'
                    && <button onClick={() => handleJoinGroup(studyGroupItems.group_id, user_id)}>가입하기</button>}
            </div>
            <div>
                <textarea value={studyGroupItems.study_desc} style={{width:'600px', height:'300px', resize:'none' }}/>
            </div>
        </div>
    )
}

export default StudygroupDetail;
