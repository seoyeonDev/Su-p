import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../../styles/components/JoinedGroup.css';

function JoinedList() {
    const [joinedlistItems, setJoinedlistItems] = useState([]);

    useEffect(() => {
        const fetchJoinedList = async () => {
            try {
                const response = await axios.get('http://localhost:8080/joinedgroup/joinedList');
                setJoinedlistItems(response.data);
            } catch (error) {
                console.error('Error fetching joined list:', error);
            }
        };

        fetchJoinedList();
    }, []);

    return (
        <div className="joined-list">
            {/*{joinedlistItems.map((item) => (*/}
            {/*    <div key={item.group_id}>{item.user_id}</div>*/}
            {/*))}*/}
        </div>
    );
}

export default JoinedList;
