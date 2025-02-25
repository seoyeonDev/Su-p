import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from "react-router-dom";
import axios from 'axios';
import { showMsgAlert } from "../Common";

function UpdateStudygroup() {
    const [formData, setFormData] = useState({
        group_id: '',
        leader_id: '',      // 로그인 한 아이디 넣기
        title: '',          // 스터디제목
        name: '',           // 스터디명
        study_desc: '',
        kind: '',           //아직 구현 미정
        status: '',
        mem_cnt: 0,
        startdate: '',      // 시작일
        enddate: '',
        view_cnt: 0,        // 조회수 
        chk_m: '',          // 제출 기준
        chk_min_cnt: 0,     // 제출최소횟수
        chk_total_cnt: 0,   // 전체제출횟수
        penalty: 0,         // 패널티 기준
    });

    const { groupId } = useParams();
    const [userId, setUserId] = useState('');
    const navigate = useNavigate();
    const [isSubmitting, setIsSubmitting] = useState(false);

    useEffect(() => {
        const fetchData = async () => { 
            try {
                const response = await axios.get('http://localhost:3000/studygroup/studyInfo', {
                    params: {
                        group_id: groupId
                    }
                });
                setFormData(prevFormData => ({
                    ...prevFormData,
                    group_id: groupId,
                    leader_id: response.data.groupVo.leader_id,
                    title: response.data.groupVo.title,
                    name: response.data.groupVo.name,
                    study_desc: response.data.groupVo.study_desc,
                    mem_cnt: response.data.groupVo.mem_cnt,
                    penalty: response.data.groupVo.penalty,
                    chk_m: response.data.groupVo.chk_m,
                    chk_min_cnt: response.data.groupVo.chk_min_cnt,
                    startdate: response.data.groupVo.startdate.split('T')[0],
                    enddate: response.data.groupVo.enddate.split('T')[0]
                }));
            } catch(error) {

            }
        };
        fetchData();
    }, []);

    // 기타 정보 핸들링
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    // 제출기준 핸들링
    const handleChkmChange = (e) => {
        const { value } = e.target;
        setFormData({
            ...formData,
            chk_m: value,
            startdate: '',
            enddate: '' // 시작일 변경 시 종료일 초기화
        });
    };

    // 시작날짜 핸들링
    const handleStartDateChange = (e) => {
        const { value } = e.target;
        setFormData({
            ...formData,
            startdate: value,
            enddate: '' // 시작일 변경 시 종료일 초기화
        });
    };

    // 시작일과 제출기준에 따른 선택 가능한 종료날짜 핸들링
    const isValidEndDate = (startDate, endDate) => {
        const start = new Date(startDate);
        const end = new Date(endDate);
    
        if (isNaN(start.getTime()) || isNaN(end.getTime())) {
            return false;
        }
    
        const diffInDays = Math.floor((end - start) / (1000 * 60 * 60 * 24));

        const isDateIntervalValid = (interval) => {
            return (Math.floor((end - start) / (1000 * 60 * 60 * 24)) + 1) % interval === 0;
        };
    
        if (formData.chk_m === 'SUBM10') {
            return isDateIntervalValid(7);
        } else if (formData.chk_m === 'SUBM20') {
            return isDateIntervalValid(30);
        }
    
        return false;
    };

    // 선택한 종료날짜 핸들링
    const handleEndDateChange = (e) => {
        const { value } = e.target;
        if(isValidEndDate(formData.startdate, value)){
            setFormData({
                ...formData,
                enddate: value,
            });
        } else {
            alert('종료일은 선택된 제출 기준에 맞는 날짜여야 합니다.');
        }
    };

    // 시작일 = 오늘 이후
    const getTodayDate = () => {
        const today = new Date();
        return today.toISOString().split('T')[0];
    };

    // 시작일 기준 종료일 최소 선택 가능 날짜
    const getMinEndDate = (startDate) => {
        const datePart = startDate.split('T')[0];
        const minDate = new Date(datePart);
        minDate.setDate(minDate.getDate() + 1); // 시작일 이후 하루
        return minDate.toISOString().split('T')[0];
    };

    // 취소
    const handleCancle = () => {
        navigate('/');
    };

    // 저장 
    const handleSubmit = () => {
        setFormData((prevFormData) => ({
            ...prevFormData,
            startdate: prevFormData.startdate + 'T00:00:00',
            enddate: prevFormData.enddate + 'T23:59:59'
        }));
        setIsSubmitting(true);
    };

    useEffect(() => {
        if (isSubmitting) {
            if (formData.title && formData.name && formData.mem_cnt > 0 && formData.chk_m && formData.chk_min_cnt > 0 && formData.startdate && formData.enddate && formData.study_desc) {
                axios.post('http://localhost:3000/studygroup/updateGroup', formData)
                    .then(response => {
                        if (response.status === 200) {
                            navigate('/');
                        }
                    });
            } else {
                showMsgAlert('스터디 그룹 생성 실패', '그룹 수정에 필요한 모든 정보를 입력해주세요.');
            }
            setIsSubmitting(false);
        }
    }, [formData, isSubmitting, navigate]);

    return (
        <div>
            <div>
                <div>
                    <label>모집글 제목</label>
                    <input type="text" name="title" value={formData.title} onChange={handleChange} />
                </div>
                <div>
                    <label>스터디명</label>
                    <input type="text" name="name" value={formData.name} onChange={handleChange} />
                </div>
                <div>
                    <div>
                        <label>모집인원</label>
                        <input type="number" name="mem_cnt" value={formData.mem_cnt} onChange={handleChange} min="0" />
                    </div>
                    <div>
                        <label>패널티</label>
                        <input type="number" name="penalty" value={formData.penalty} onChange={handleChange} min="0" /> {/* 숫자만 */}
                    </div>
                </div>
                <div>
                    <div>
                        <label htmlFor="chk_m">제출기준</label>
                        <select id="chk_m" name="chk_m" onChange={handleChkmChange} value={formData.chk_m}>
                            <option value="">기준 선택</option>
                            <option value="SUBM10">일주일 단위</option>
                            <option value="SUBM20">한 달 단위</option>
                        </select>
                    </div>
                    <div>
                        <label>제출횟수</label>
                        <input type="number" name="chk_min_cnt" value={formData.chk_min_cnt} onChange={handleChange} min="0" /> {/* 숫자만 */}
                    </div>
                </div>
                <div>
                    <div>
                        <label>시작일</label>
                        <input 
                            type="date" 
                            name="startdate" 
                            value={formData.startdate} 
                            onChange={handleStartDateChange} 
                            min={getTodayDate()} 
                        />
                    </div>
                    <div>
                        <label>종료일</label>
                        <input 
                            type="date" 
                            name="enddate" 
                            value={formData.enddate} 
                            onChange={handleEndDateChange} 
                            min={(formData.startdate && formData.chk_m) ? getMinEndDate(formData.startdate) : getTodayDate()} 
                            disabled={!formData.startdate || !formData.chk_m} // 시작일이 없으면 비활성화
                        />
                    </div>
                </div>
                <div>
                    <div>
                        <label>스터디 설명</label>
                        <textarea name="study_desc" value={formData.study_desc} onChange={handleChange}></textarea>
                    </div>
                </div>
                <div>
                    <button onClick={handleCancle}>취소</button>
                    <button onClick={handleSubmit}>수정</button>
                </div>
            </div>
        </div>
    );
}

export default UpdateStudygroup;