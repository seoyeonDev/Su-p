import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../../../styles/mystudy/detail/MyStudyAdminAfterOpen.css';


const MyStudyAdminAfterOpen = ({ selectedContent, group_id }) => {
  const [penaltylogInfoList, setPenaltylogInfoList] = useState([]);

  const STATUS = {
    EXPELLED: "PERM40"
  };

  const getJoinedgroupList = async () => {
    try {
      const response = await axios.get('http://localhost:8080/joinedgroup/groupOwnerJoinedList', {
        params: {
          group_id: group_id,
          user_id: localStorage.getItem('user_id')
        }
      });

      if (response.status === 200) {
        setPenaltylogInfoList(response.data.penaltylogInfoList);
      }
    } catch (error) {
      console.error('API call failed:', error);
    }
  }



  useEffect(() => {
    if (selectedContent === '스터디 관리') {
      getJoinedgroupList();
    }
  }, [group_id, selectedContent]);

  const updateUserStatus = async (user_id, join_status) => {
    try {
      const response = await axios.put('http://localhost:8080/joinedgroup/updateStatus', null, {
        params: {
          user_id: user_id,
          group_id: group_id,
          status: join_status
        }
      });

      if (response.status === 200) {
        getJoinedgroupList();
      }

    } catch (error) {
      console.error('API call failed:', error);
    }
  }

  return (
    <div className={'common-content'}>
      {selectedContent === '스터디 관리' &&
        <div>
          {penaltylogInfoList.map((data) => (
            <div key={data.user_id}>
              <div className="button-list-container">
              <p>{data.nickname}</p>
              <p>{data.penaltylogList ? data.penaltylogList.length : 0}</p>
                <button className="hover-button" onClick={() => updateUserStatus(data.user_id.trim(), STATUS.EXPELLED)}>
                  강퇴
                </button>
                {data.penaltylogList && data.penaltylogList.length > 0 && (
                  <ul className="hidden-list">
                    {data.penaltylogList.map((log, index) => (
                      <li key={index}>
                        <p>{log.penalty_round}회차 : {log.logcontent}</p>
                      </li>
                    ))}
                  </ul>
                )}
              </div>
            </div>
          ))}

        </div>
      }

    </div>
  );
}

MyStudyAdminAfterOpen.propTypes = {

};

export default MyStudyAdminAfterOpen;