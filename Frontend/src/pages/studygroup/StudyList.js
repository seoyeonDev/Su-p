import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios';
import '../../styles/studygroup/StudyList.css';
import { getIdFromLocalStorage, showConfirmationAlert } from "../Common";

const StudyList = () => {
    const navigate = useNavigate();
    const [studyItems, setStudyItems] = useState([]);
    const [title, setTitle] = useState('');
    const [isSortByViews, setIsSortByViews] = useState(false);
    const [isSortByLatest, setIsSortByLatest] = useState(false);
    const user_id = getIdFromLocalStorage();

    useEffect(() => {
        const fecthData = async () => {
            try {
                const response = await axios.get('http://localhost:3000/studygroup/studyGroupList');
                setStudyItems(response.data);

            } catch (error) {
                console.error('Error fetching data:', error)
            }
        }
        fecthData();

    }, []);

    useEffect(() => {
        const fetchData = async () => {
            try {

                const response = await axios.get('http://localhost:3000/studygroup/list', {
                    params: {
                        title: title.trim(),
                        isSortByViews,
                        isSortByLatest
                    }
                });
                setStudyItems(response.data);

            } catch (error) {
                console.error('Error fetching data:', error)
            }
        }

        fetchData();
    }, [title, isSortByViews, isSortByLatest]);
    
    // 스터디그룹 상세보기
    const handleStudyGroupDetail = (groupId) => {
        navigate(`/studygroupDetail/${groupId}`);
    }

    // 스터디그룹 검색하기
    const handleInputChange = (event) => {
        setTitle(event.target.value);
    }

    // 조회순으로 정렬
    const handleSortByViews = () => {
        setIsSortByViews(true);
        setIsSortByLatest(false);
    };

    // 최신순으로 정렬
    const handleSortByLatest = () => {
        setIsSortByViews(false);
        setIsSortByLatest(true);
    };

    // 날짜 포맷 함수
    const formatDate = (dateString) => {
        const date = new Date(dateString);
        return date.toLocaleDateString('ko-KR', {
            year: 'numeric',
            month: '2-digit',
            day: '2-digit',
        }).replace(/\./g, '.'); // .으로 구분
    };

    // 스터디 D-day - dateString와 현재 날짜의 차이를 일(day) 단위로 계산하기
    const chkDate = (dateString) => {
        const date = new Date(dateString);
        const now = new Date();
        console.log(date);
        console.log(now);
        const diff = Math.floor((date-now) / (1000 * 60 * 60 * 24));
        if (diff < 0){
            return null;
        }
        return diff;
    }

    // 스터디 그룹 생성 버튼 클릭 시, 사용자가 로그인하지 않은 경우 모달창이 나타난다.
    const handleCreateStudygroup = () => {
        if(user_id === null){
            showConfirmationAlert("", "로그인이 필요한 서비스입니다. 지금 로그인하시겠습니까?", "warning", "예", "아니오")
            .then((result) => {
                if (result) {
                    navigate("/login");
                } 
            }).catch((error) => {
                console.error("에러 발생:", error);
            });
        } else {
            navigate("/createStudygroup");
        }
    }

    return (
        <div className='common-content-container'>
            <div className='common-content'>
                <div>
                    검색 <input type="text" value={title} onChange={handleInputChange} />
                </div>
                <button onClick={handleSortByViews}>
                    조회순
                </button>
                <button onClick={handleSortByLatest}>
                    최신순
                </button>
                <div><button onClick={handleCreateStudygroup}>글쓰기</button><br /></div>
                <div className='study-card-container'>
                    {studyItems.length > 0 ? (
                        studyItems.map(item => {
                            const Dday = chkDate(item.startdate);
                            return (
                                <div key={item.group_id} className='study-card' onClick={() => handleStudyGroupDetail(item.group_id)}>
                                    {Dday !== null && (
                                        <h4>모집 마감일 &emsp;&ensp; D - {Dday}</h4>
                                    )}
                                    <p>{item.status_nm}</p>
                                    <h4>{item.title}</h4>
                                    <p>{item.study_desc}</p>
                                    <p>작성자 &emsp;&ensp; {item.leader_id}</p>
                                    <p>조회수 &emsp;&ensp; {item.view_cnt}</p>
                                    <p>스터디 시작일 &emsp;&ensp; {formatDate(item.startdate)}</p>
                                    <p>스터디 종료일 &emsp;&ensp;{formatDate(item.enddate)}</p>

                                </div>
                            );
                        })
                    ) : (
                        <p>No study groups found.</p>
                    )}
                </div>
            </div>

        </div>
    );
}

export default StudyList;