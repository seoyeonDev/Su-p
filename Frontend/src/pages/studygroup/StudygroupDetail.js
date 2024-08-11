import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

const StudygroupDetail = () => {
    const { groupId } = useParams();
    const navigate = useNavigate();
    const [studyGroupItems, setStudyGroupItems] = useState([]);
    const [studyGroupStatus, setStudyGroupStatus] = useState();
    const [studyGroupStartdate, setStudyGroupStartdate] = useState();
    const [studyGroupEnddate, setStudyGroupEnddate] = useState();
    const user_id = localStorage.getItem('user_id');

    useEffect(() => {
        const fecthData = async () => {
            try {
                const response = await axios.get('http://localhost:3000/studygroup/studyDetail', {
                    params: {
                        group_id: groupId,
                        user_id
                    }
                });
                setStudyGroupItems(response.data.vo);
                setStudyGroupStartdate(response.data.vo.startdate.split('T')[0]);
                setStudyGroupEnddate(response.data.vo.enddate.split('T')[0]);
                setStudyGroupStatus(response.data.status);
            } catch (error) {
                console.error('Error fetching data:', error)
            }
        }
        fecthData();
    }, [groupId]);

    // 수정 페이지로 이동
    const handleUpdateGroup = (groupId) => {
        navigate(`/updateStudygroup/${groupId}`);
    };

    // 그룹 삭제
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

    const currentDate = new Date();
    const formattedDate = `${currentDate.getFullYear()}.${String(currentDate.getMonth() + 1).padStart(2, '0')}.${String(currentDate.getDate()).padStart(2, '0')}`;

    return (
        <div>
            <div>
                <h2>{studyGroupItems.title}</h2>
                <h4>{studyGroupItems.leader_id} &nbsp;&nbsp;|&nbsp;&nbsp; {formattedDate} &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 조회 {studyGroupItems.view_cnt}</h4>
            </div> <br/>
            <div>
                <h4>모집인원 &nbsp;&nbsp;&nbsp; {studyGroupItems.mem_cnt}</h4>
                <h4>시작일 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {studyGroupStartdate}</h4>
                <h4>종료일 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; {studyGroupEnddate}</h4>
            </div>
            <div>
                {studyGroupStatus === 1 && (
                    <>
                        <button onClick={() => handleUpdateGroup(studyGroupItems.group_id)}>수정</button>
                        <button onClick={() => handleDeleteGroup(studyGroupItems.group_id, studyGroupItems.leader_id)}>삭제</button>
                    </>
                )}
                {studyGroupStatus === 2 && <button>가입취소</button>}
                {studyGroupStatus === 3 && <button>가입하기</button>}
            </div>
            <div>
                <textarea value={studyGroupItems.study_desc} style={{width:'600px', height:'300px', resize:'none' }}/>
            </div>
        </div>
    )
}

export default StudygroupDetail;