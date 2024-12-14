import React, { useEffect, useState } from 'react';
import axios from "axios";

const MyStudyAdminBeforeOpen = ({ selectedContent, group_id }) => {
    const [joinApprovedList, setJoinApprovedList] = useState([]);
    const [joinRequestList, setJoinRequestList] = useState([]);
    
    const STATUS = {
        APPROVED: "PERM20",
        REJECTED: "PERM30",
    };

    const getStudyGroupJoinList = async (group_id) => {
        try {
            const user_id = localStorage.getItem('user_id');

            const response = await axios.get('http://localhost:8080/joinedgroup/groupOwnerJoinedList', {
                params: {
                    group_id: group_id,
                    user_id: user_id
                }
            });

            if (response.status === 200) {
                setJoinApprovedList(response.data.joinApprovedList);
                setJoinRequestList(response.data.joinRequestList);
            }
        } catch (error) {

        }
    }

    useEffect(() => {
        if(selectedContent === "스터디 관리"){
            getStudyGroupJoinList();
        }
    }, [selectedContent, group_id]);

    const updateUserStatus = async (user_id, join_status) => {
        try {
            const response = await axios.put('http://localhost:8080/joinedgroup/updateStatus', null, {
                params: {
                    user_id: user_id,
                    group_id: group_id,
                    status: join_status
                }
            });

            if(response.status === 200){
                getStudyGroupJoinList();
            }
    
        } catch (error) {
            console.error('API call failed:', error);
        }
    } 

    return (
        <div className={'common-content'}>
            {selectedContent === '스터디 관리' &&
                <div>
                    <div>
                        {/* 가입 신청 목록 */}
                        <div>
                            <h2>가입 신청 목록</h2>
                            {joinRequestList && joinRequestList.map((item)=> (
                                
                                <li key={item.user_id}>
                                    {item.nickname}
                                    <button onClick={() => updateUserStatus (item.user_id, STATUS.APPROVED)}>
                                        추가
                                    </button>
                                    <button onClick={() => updateUserStatus (item.user_id, STATUS.REJECTED)}>
                                        거절
                                    </button>    
                                </li>
                            ))}
                        </div>
                        {/* 가입 승인 목록 */}
                        <div>
                            <h2>멤버</h2>
                            {joinApprovedList && joinApprovedList.map((item) =>(
                                 <li key={item.user_id}>
                                    {item.nickname}
                                </li>
                            ))}
                        </div>
                    </div>


                </div>
            }
        </div>
    );
}

MyStudyAdminBeforeOpen.propTypes = {

};

export default MyStudyAdminBeforeOpen;