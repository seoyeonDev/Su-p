import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from "react-router-dom";
import axios from 'axios';
import '../../styles/studygroup/StudyList.css';

const StudyList = () => {
    const navigate = useNavigate();
    const [studyItems, setStudyItems] = useState([]);
    const [title, setTitle] = useState('');
    const [isSortByViews, setIsSortByViews] = useState(false);
    const [isSortByLatest, setIsSortByLatest] = useState(false);

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
    
    const handleStudyGroupDetail = (groupId) => {
        navigate(`/studygroupDetail/${groupId}`);
    }

    const handleInputChange = (event) => {
        setTitle(event.target.value);
    }

    const handleSortByViews = () => {
        setIsSortByViews(true);
        setIsSortByLatest(false);
    };

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
                <div><Link to={"/createStudygroup"}>글쓰기</Link><br /></div>
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