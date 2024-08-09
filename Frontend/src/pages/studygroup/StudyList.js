import React, { useEffect, useState } from 'react';
import { Link } from "react-router-dom";
import axios from 'axios';
import '../../styles/studygroup/StudyList.css';

const StudyList = () => {
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
                        studyItems.map(item => (
                            <div key={item.group_id} className='study-card'>

                                <h4>{item.title}</h4>
                                <p>{item.study_desc}</p>

                            </div>
                        ))
                    ) : (
                        <p>No study groups found.</p>
                    )}
                </div>
            </div>

        </div>
    );
}

export default StudyList;