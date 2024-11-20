// App.js
import React, { useState } from 'react';
import Header from './MyStudyHeader';
import Content from './MyStudyList';
import axios from "axios";
import MyStudyHeader from "./MyStudyHeader";
import MyStudyList from "./MyStudyList";

const Test = () => {
    // const [selectedContent, setContents] = useState([]);
    const [selectedContent, setSelectedContent] = useState(['전체 스터디']);
    const [msg, setMsg] = useState('');

    const getJoinedList = () => {
        console.log('selectJoinedList')
        axios.get('http://localhost:3000/joinedgroup/joinedList')
            .then (response => {
                if (response.status === 200){
                    setMsg(response.data.msg);
                    alert('목록을 가져왔습니다 !');
                    setSelectedContent((response.data));    // 응답 저장

                }else{
                    setMsg (response.data);
                    alert(response.data);
                }
            })
    }

    // const [joinedStatus,setJoinedStatus] = useState();
    // const [role,setRole] = useState();
    // const [status,setStatus] = useState();


    const handleSelect = async(buttonName, joinstatus, role, status) => {
        console.log('selectedTest : ',joinstatus,role,status);
        try {
            const response = await axios.get('http://localhost:3000/joinedgroup/selectList',{
                params: {
                    joinstatus: joinstatus,
                    role: role,
                    status: status
                }
            });
            setSelectedContent(response.data.list);
            console.log(response.data)
        } catch (error) {
            console.error('Error fetching list:', error);
        }

    }

    return (
        // <div>
        //     <Header title="나의 스터디" onSelect={setSelectedContent} />
        //     <Content selectedContent={selectedContent} />
        // </div>
        <div>
            <MyStudyHeader title = "나의 스터디" onSelect={handleSelect} />
            <MyStudyList selectedContent={selectedContent}/>
        </div>
    );
};

export default Test;
