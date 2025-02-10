import React, { useState, useEffect } from 'react';
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import { getIdFromLocalStorage } from "../Common";
import DatePicker from "react-datepicker";
import 'react-datepicker/dist/react-datepicker.css';

function CreateStudygroup() {
    const [formData, setFormData] = useState({
        group_id: '',
        leader_id: '',      // 로그인 한 아이디 넣기
        title: '',          // 스터디제목
        name: '',           // 스터디명
        study_desc: '',     // 설명
        kind: '',           // 스터디 종류 (아직 구현 미정)
        status: '',
        mem_cnt: 0,
        startdate: null,    // 시작일
        enddate: null,      // 종료일
        view_cnt: 0,        // 조회수 
        chk_m: '',          // 제출 기준
        chk_min_cnt: 0,     // 제출최소횟수
        chk_total_cnt: 0,   // 전체제출횟수
        penalty: 0,         // 패널티 기준
    });

    const navigate = useNavigate();
    const [isSubmitting, setIsSubmitting] = useState(false);

    const [interval, setInterval] = useState('');           // 제출 기준 : 7 or 30
    const [availableDate, setAvailableDate] = useState([]); // 종료일 선택 가능 날짜 

    useEffect(() => {
        if (isSubmitting) {
            // TODO. SWEETALERT으로 변경하기
            if (!window.confirm("스터디 생성 후 종료일까지 탈퇴 불가합니다. 진행할까요?")) {
                console.log("스터디 생성 안 함");
                return;
            } 

            if (formData.title && formData.name && formData.mem_cnt && formData.chk_m && formData.chk_min_cnt && formData.startdate && formData.enddate && formData.study_desc) {
                axios.post('http://localhost:3000/studygroup/createGroup', formData)
                    .then(response => {
                        if (response.status === 200) {
                            navigate('/');
                        }
                    })
                    .catch(error => {
                        alert('서버 요청에 실패했습니다.' + error);
                        console.log(error);
                    });;
            } else {
                alert('그룹 생성에 필요한 모든 정보를 입력해주세요.');
            }
            setIsSubmitting(false);
        }
    }, [formData, isSubmitting, navigate]);

    // 기타 정보 핸들링
    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        });
    };

    // 취소
    const handleCancle = () => {
        navigate('/');
    };

    // 저장 
    const handleSubmit = () => {
        if (formData.startdate && formData.enddate) {
            const formattedStartDate = formatDateForServer(formData.startdate, true); // 시작일 포맷팅
            const formattedEndDate = formatDateForServer(formData.enddate, false); // 종료일 포맷팅

            setFormData(prevFormData => ({
                ...prevFormData,
                startdate: formattedStartDate,
                enddate: formattedEndDate,
                leader_id: getIdFromLocalStorage()
            }));
        }
        setIsSubmitting(true);
    };

    // 날짜 포맷팅 함수 (LocalDateTime에 맞게 변환)
    const formatDateForServer = (date, isStart = true) => {
        const updatedDate = new Date(date);

        // 시작일과 종료일에 따라 시간 조정
        updatedDate.setHours(isStart ? 0 : 23, isStart ? 0 : 59, isStart ? 0 : 59, isStart ? 0 : 999);
        updatedDate.setHours(updatedDate.getHours() + 9); // 한국 표준시로 바꾸기
        // 'yyyy-MM-dd'T'HH:mm:ss' 포맷으로 반환
        return updatedDate.toISOString().slice(0, 19); // 마지막 초, 밀리초 제거

    };

    // 시작일 선택
    const startDateChange = (startDate) => {
        setFormData(prevFormData => ({
            ...prevFormData,
            startdate: new Date(startDate),
            enddate: null,
            chk_m: null
        }));
    }

    // 종료일 선택
    const endDateChange = (endDate) => {
        setFormData(prevFormData => ({
            ...prevFormData,
            enddate: new Date(endDate)
        }));
    }

    // 제출 기준 선택
    const handleIntervalChange = (e) => {
        let days = parseInt(e.target.value, 10);
        setInterval(days);

        let chk_m = days === 7 ? 'SUBM10' : 'SUBM20';

        setFormData(prevFormData => ({
            ...prevFormData,
            enddate: null,
            chk_m: chk_m
        }));

        if (formData.startdate) {
            calculateAvailableDates(formData.startdate, days);
        }
    }

    // 시작일 & 제출 기준을 토대로, 종료일 선택 가능 날짜 계산하기
    const calculateAvailableDates = (startDate, interval) => {
        const dates = [];
        let currentDate = new Date(startDate);
        let firstInterval = interval === 7 ? 6 : 29; // 첫 번째 간격 설정 (7일 기준: 6일, 30일 기준: 29일)
        let length = interval === 7 ? 120 : 30; // (약 2년 5개월 뒤까지 availableDate setting)

        currentDate.setDate(currentDate.getDate() + firstInterval);
        dates.push(new Date(currentDate));

        for (let i = 1; i < length; i++) {
            currentDate.setDate(currentDate.getDate() + interval);
            dates.push(new Date(currentDate));
        }

        setAvailableDate(dates);
    }

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
                        <input type="number" name="mem_cnt" value={formData.mem_cnt} onChange={handleChange} min="0" />
                    </div>
                    <div>
                        <label>패널티 상한선</label>
                        <input type="number" name="penalty" value={formData.penalty} onChange={handleChange} min="0" /> {/* 숫자만 */}
                    </div>
                </div>
                <div>
                    <div>
                        <label>제출횟수</label>
                        <input type="number" name="chk_min_cnt" value={formData.chk_min_cnt} onChange={handleChange} min="0" /> {/* 숫자만 */}
                    </div>
                </div>
                <div>
                    <div>
                        <label>시작일</label>
                        <DatePicker
                            selected={formData.startdate ? new Date(formData.startdate) : null}
                            onChange={startDateChange}
                            dateFormat="yyyy-MM-dd"
                            minDate={new Date()}
                        />
                    </div>
                    <div>
                        <label>제출 기준</label>
                        <select value={interval || ""} onChange={handleIntervalChange}>
                            <option value="" disabled>선택</option>
                            <option value={7}>7일</option>
                            <option value={30}>30일</option>
                        </select>
                    </div>
                    <div>
                        <label>종료일</label>
                        <DatePicker
                            selected={formData.enddate ? new Date(formData.enddate) : null}
                            onChange={endDateChange}
                            dateFormat="yyyy-MM-dd"
                            includeDates={availableDate}
                            disabled={!formData.startdate || !formData.chk_m}
                        />
                    </div>
                </div>
                <div>
                    <div>
                        <label>소개</label>
                        <textarea name="study_desc" value={formData.study_desc} onChange={handleChange}></textarea>
                    </div>
                </div>
                <div>
                    <button onClick={handleCancle}>취소</button>
                    <button onClick={handleSubmit}>등록</button>
                </div>
            </div>
        </div>
    );
}

export default CreateStudygroup;