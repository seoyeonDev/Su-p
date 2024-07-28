import React, { useState, useEffect } from 'react';
//import axios from 'axios';


function CreateStudygroup() {
    const [formData, setFormData] = useState({
        group_id: '',
        leader_id: '',      // 로그인 한 아이디 넣기
        title: '',          // 스터디제목
        name: '',           // 스터디명
        study_desc: '',
        kind: '',           //아직 구현 미정
        status: '',
        mem_cnt: 0,
        startdate: '',      // 활성화 상태
        enddate: '',
        view_cnt: 0,        // 조회수 
        chk_m: '',          // 제출 기준
        chk_min_cnt: 0,     // 제출최소횟수
        chk_total_cnt: 0,   // 전체제출횟수
        penalty: 0,         // 패널티 기준
    });

    const [userId, setUserId] = useState(''); 

    useEffect(() => {
        // 현재 로그인 한 아이디 가져오기 
        const storedUserId = localStorage.getItem('user_id');
        if (storedUserId) {
            setUserId(storedUserId); 

            setFormData(prevFormData => ({ // leader_id를 현재 로그인 한 아이디로 넣기 
                ...prevFormData,
                leader_id: storedUserId
            }));
        }
    }, []); // 빈 배열: 컴포넌트가 처음 렌더링될 때만 실행


    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    // 저장 
    const handleSubmit = () => {
        // 'http://localhost:3000/studygroup/createGroup'
    };

    return (
        <div>
            <div>
                <div>
                    <label>제목</label>
                    <input type="text" name="title" value={formData.title} onChange={handleChange} />
                </div>
                <div>
                    <label>스터디명</label>
                    <input type="text" name="name" value={formData.name} onChange={handleChange} />
                </div>
                <div>
                    <div>
                        <label>모집인원</label>
                        <input type="text" name="mem_cnt" value={formData.mem_cnt} onChange={handleChange} />
                    </div>
                    <div>
                        <label>패널티</label>
                        <input type="number" name="penalty" value={formData.penalty} onChange={handleChange} /> {/* 숫자만 */}
                    </div>
                </div>
                <div>
                    <div>
                        <label htmlFor="chk_m">제출기준</label>
                        <select id="chk_m" name="chk_m" onChange={handleChange}>
                            <option value="">기준 선택</option>
                            <option value="SUBM10">일주일 단위</option>
                            <option value="SUBM20">한 달 단위</option>
                        </select>
                    </div>
                    <div>
                        <label>제출횟수</label>
                        <input type="number" name="chk_min_cnt" value={formData.chk_min_cnt} onChange={handleChange} /> {/* 숫자만 */}
                    </div>
                </div>
                <div>
                    <div>
                        <label>시작일</label>
                        <input type="date" name="startdate" value={formData.startdate} onChange={handleChange} />
                    </div>
                    <div>
                        <label>종료일</label>
                        <input type="date" name="enddate" value={formData.enddate} onChange={handleChange} />
                    </div>
                </div>
                <div>
                    <div>
                        <label>소개</label>
                        <textarea name="study_desc" value={formData.study_desc} onChange={handleChange}></textarea>
                    </div>
                </div>
                <div>
                    <button>취소</button>
                    <button onClick={handleSubmit}>등록</button>
                </div>
            </div>
        </div>
    );
}

export default CreateStudygroup;