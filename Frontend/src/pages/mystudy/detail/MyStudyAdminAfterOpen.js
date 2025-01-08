import React, { useState, useEffect } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';
import '../../../styles/mystudy/detail/MyStudyAdminAfterOpen.css';
import {getIdFromLocalStorage} from "../../Common";

const MyStudyAdminAfterOpen = ({ selectedContent, group_id }) => {
  const [penaltylogInfoList, setPenaltylogInfoList] = useState([]);
  const user_id = getIdFromLocalStorage();

  const STATUS = {
    EXPELLED: "PERM40"
  };

  useEffect(() => {
    if (selectedContent === '스터디 관리') {
      getJoinedgroupList();
    }
  }, [group_id, selectedContent]);

  /**
   * 오픈 후, 
   */
  const getJoinedgroupList = async () => {
    try {
      const response = await axios.get('http://localhost:8080/joinedgroup/groupOwnerJoinedList', {
        params: {
          group_id: group_id,
          user_id: getIdFromLocalStorage()
        }
      });

      if (response.status === 200) {
        setPenaltylogInfoList(response.data.penaltylogInfoList);
      }
    } catch (error) {
      console.error('API call failed:', error);
    }
  }

  /**
   * 관리자 전용 - 특정 사용자의 가입 상태를 변경한다
   * @param {String} user_id 가입 상태를 변경 할 유저 ID
   * @param {String} join_status 변경할 가입 상태 값 (예: "PERM40")
   */
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

  /**
   * 관리자 전용 - 특정 사용자의 패널티 기록을 초기화 
   * @param {string} user_id 패널티를 리셋할 사용자의 ID
   */
  const resetPenalty = async (user_id) => {
    try {
      const response = await axios.delete(`http://localhost:8080/penaltylog/${group_id}/${user_id}`);

      if (response.status === 200) {
        getJoinedgroupList();
        alert(user_id + "님의 패널티가 성공적으로 리셋되었습니다.");
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
                {data.user_id != user_id && (
                  <>
                    <button className="hover-button" onClick={() => updateUserStatus(data.user_id.trim(), STATUS.EXPELLED)}>
                      강퇴
                    </button>
                  </>
                )}
                {data.user_id === user_id && (
                  <>
                    <p className="study-admin">관리자</p>
                  </>
                )}
                
                {data.penaltylogList && data.penaltylogList.length > 0 && (
                  <>
                    <ul className="hidden-list">
                      {data.penaltylogList.map((log, index) => (
                        <li key={index}>
                          <p>{log.penalty_round}회차 : {log.logcontent}</p>
                        </li>
                      ))}
                    </ul>
                    <button onClick={() => resetPenalty(data.user_id.trim())}>리셋</button>
                  </>
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
  selectedContent: PropTypes.string.isRequired,
  group_id: PropTypes.string.isRequired,
};

export default MyStudyAdminAfterOpen;