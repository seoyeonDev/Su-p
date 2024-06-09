import React, { useEffect, useState } from 'react';
import axios from 'axios';

const StudyList = () => {
    const [studyItems, setStudyItems] = useState([]);

    useEffect(() => {
      // 서버단에서 데이터 가져오기
      axios.post('http://localhost:8080/studygroup/studyGroupList')
        .then(response => {
          setStudyItems(response.data.studylist); // studylist 키에 해당하는 데이터를 상태에 저장
        })
        .catch(error => console.error('Error fetching data:', error));
    }, []);

    return (
         <div className='study-list'>
            <br/>
            <div>선호: 스터디 리스트 Home.js에 불러오기 테스트</div>
            <br/>
            {studyItems.map(item => (
                <div key={item.group_id} className='study-card'>
                    <h3>{item.title}</h3>
                    <p>{item.study_desc}</p>
                </div>
            ))}
        </div>
    );
}

export default StudyList;